#!/usr/bin/perl

use strict;
use warnings;
use POSIX qw(strftime);
use File::stat;
use Digest::MD5 'md5_hex';
use Net::MySQL;
use Net::SSH::Perl;
use File::Temp;
use LWP::UserAgent;
use Getopt::Long;
use HTTP::Request;

my $ApachePath = "/etc/apache2";
my $ConfigPath = "$ApachePath/mods-available/proxy.conf";
my $LogPath = "updater.log";
my $ApacheHardRestartCmd = '/etc/init.d/apache2 graceful';
my $ApacheGracefulRestartCmd = '/etc/init.d/apache2 restart';

my $DatabaseHostname = undef;
my $DatabaseUsername = undef;
my $DatabaseName = undef;
my $DatabasePassword = undef;
my $LoadBalancerPassword = undef;
my $LoadBalancerHostname = undef;
my $ErrorMessage = undef;
my $DisplayHelp = undef;
my $Usage = "usage: $0 [--help] [--dbhost=hostname] [--dbname=name]
[--dbpass=password] [--lbpass=password] [--lbhost=hostname] [--dbuser]

	--help		Displays this help dialog.
	--dbhost	Specifies the db hostname.
	--dbname	Specifies the db name.
	--dbpass	Specifies the db password.
	--lbpass	Specifies the load balancer
			root password.
	--lbhost	Specifies the hostname of the
			load balancer.

";

GetOptions("dbhost=s" => \$DatabaseHostname, "dbuser=s" => \$DatabaseUsername, "dbname=s" => \$DatabaseName, "dbpass=s" => \$DatabasePassword,
"lbpass=s" => \$LoadBalancerPassword, "lbhost=s" => \$LoadBalancerHostname, "help" => \$DisplayHelp);

if (defined $DisplayHelp) {
	print $Usage;
	exit 0;
}

$ErrorMessage = "Must specify a db hostname." unless defined $DatabaseHostname;
$ErrorMessage = "Must specify a db name." unless defined $DatabaseName;
$ErrorMessage = "Must specify a db password." unless defined $DatabasePassword;
$ErrorMessage = "Must specify a db username." unless defined $DatabaseUsername;
$ErrorMessage = "Must specify a load balancer root password." unless defined $LoadBalancerPassword;
$ErrorMessage = "Must specify a load balancer hostname." unless defined $LoadBalancerHostname;

if (defined $ErrorMessage) {
	print STDERR "Error: $ErrorMessage\n";
	print $Usage;
	exit -1;
}

my $UA = LWP::UserAgent->new;
$UA->timeout(1);

our $SSH = Net::SSH::Perl->new($LoadBalancerHostname);
$SSH->login('root', $LoadBalancerPassword);

my $Log;
open($Log, ">>", $LogPath) or die "Unable to open logfile.\n";

my %Servers = ();

sub logwrite($)
{
	print $Log strftime('[%Y/%m/%d %H:%M:%S]', localtime) . ": $_[0]\n";
}

sub logdie($)
{
	logwrite($_[0]);
	logwrite("Aborting...");
	exit(-1);
}

sub runRemoteCmd($)
{
	my $CMD = shift;
	my ($stdout, $stderr, $exit) = $SSH->cmd($CMD);
	return undef if $exit != 0;
	return $stdout;
}

sub getRemoteSize($)
{
	my $Result = runRemoteCmd("du -b $ConfigPath");
	logdie("Unable to retrieve size.") unless defined $Result && $Result =~ m/^(\d+)/;
	return $1;
}

sub getRemoteMD5($)
{
	my $Result = runRemoteCmd("md5sum $ConfigPath");
	logdie("Unable to retrieve md5sum.") unless defined $Result && $Result =~ m/^(\w+)/;
	return $1;	
}

sub putRemoteFile($$)
{
	my $Contents = shift;
	my $Destination = shift;
	my $TMP = tmpnam();
	open(my $OUTPUT, ">$TMP") or logdie("Unable to open tempporary file for remote file transfer.");
	print $OUTPUT $Contents;
	close($OUTPUT);
	my $CMD = "scp $TMP root@" . "$LoadBalancerHostname:$Destination";
	`$CMD`;
	return $? == 0;
}

sub getRemoteFile($)
{
	my $Origin = shift;
	return runRemoteCmd("cat $Origin");
}

logwrite("Updating server list...");
# Get server list from the DB.
my $DB = Net::MySQL->new("hostname" => $DatabaseHostname, "database" => $DatabaseName, "user" => $DatabaseUsername, "password" => $DatabasePassword);
logdie("Unable to connect to mysql database with $DatabaseHostname, $DatabaseName, $DatabaseUsername, $DatabasePassword.") unless defined $DB;
my $Result = $DB->query("SELECT * FROM application_ServerList");
logdie("Malformed Database Table:" . $DB->get_error_message) if $DB->is_error();
my $Record = $DB->create_record_iterator();
while (my $Row = $Record->each) {
	$Servers{$$Row[0]} = $$Row[1];
}

# Make sure that they're all online, and that tomcat is running.
foreach my $Server (keys(%Servers)) {
	logwrite("Checking $Server....");
	my $Request = HTTP::Request->new(GET => "http://$Server:" . $Servers{$Server} . "/controller?command=ping");
	my $Response = $UA->request($Request);
	logwrite("Received response code " . $Response->code . ".") if defined $Response;
	next if $Response->code == 204;
	delete $Servers{$Server};
	$DB->query("DELETE FROM application_ServerList WHERE hostname='$Server'");
	logdie("Unable to delete row from database: ". $DB->get_error_message) if $DB->is_error();
	logwrite("Server $Server is not responding to ping, not adding to list, and deleting from database.");
}
# Generate configuration file.
my $ConfigContents = "<IfModule mod_proxy.c>
# 'ProxyRequests On' line and the <Proxy *> block below.
# WARNING: Be careful to restrict access inside the <Proxy *> block.
# Open proxy servers are dangerous both to your network and to the
# Internet at large.
#
# If you only want to use apache2 as a reverse proxy/gateway in
# front of some web application server, you DON'T need
# 'ProxyRequests On'.

#ProxyRequests On
#<Proxy *>
#        AddDefaultCharset off
#        Order deny,allow
#        Deny from all
#        #Allow from .example.com
#</Proxy>

# Enable/disable the handling of HTTP/1.1 \"Via:\" headers.
# (\"Full\" adds the server version; \"Block\" removes all outgoing Via: headers)
# Set to one of: Off | On | Full | Block
#ProxyVia Off

ProxyPass /proxy balancer://mycluster nofailover=On
<Proxy balancer://mycluster>
";

foreach my $Server (keys(%Servers)) {
	$ConfigContents .= "	BalancerMember http://$Server:$Servers{$Server}\n";
}

$ConfigContents .= "</Proxy>

</IfModule>";

# Check if we differ from config file.
my $Differ = 1;
my $LocalSize = length($ConfigContents);
my $RemoteSize = getRemoteSize($ConfigPath);
logwrite("Local/Remote Szies: $LocalSize/$RemoteSize.");
if ($LocalSize ==  $RemoteSize) {
	# Size is the same, let's do an md5 digest of our config, and the new one.
	my $MD5Local = md5_hex($ConfigContents);
	my $MD5Remote = getRemoteMD5($ConfigPath);
	logwrite("Local/Remote Hash: $MD5Local/$MD5Remote.");
	$Differ = 0 if ($MD5Local eq $MD5Remote);
}

if ($Differ == 1) {	
	logwrite("Generated and current files differ. Replacing.");
	logdie("Unable to transfer configuration file for writing.") unless putRemoteFile($ConfigContents, $ConfigPath);
	logwrite("Configuration updated sucessfully.");
	logwrite("Gracefully restarting apache.");
	# Perform a graceful restart.
	my $ApacheLog = runRemoteCmd($ApacheGracefulRestartCmd);
	if ($? != 0) {
		logwrite("Unable to gracefully restart apache: " . $ApacheLog);
		logwrite("Attempting full restart.");
		$ApacheLog = runRemoteCmd($ApacheHardRestartCmd);
		logdie("Unable to hard restart apache!") if $? != 0; 
	}
	logwrite("Succesfully restarted apache.");
}
else {
	logwrite("Config files identical. No rewrite.");
}

