#!/usr/bin/perl

use strict;
use warnings;
use Net::Ping;
use POSIX qw(strftime);
use File::stat;
use Digest::MD5 'md5';

my $ApachePath = "/etc/apache2";
my $ConfigPath = "$ApachePath/mods-available/proxy.conf";
my $ServerPath = "servers.txt";
my $LogPath = "updater.log";
my $ApacheHardRestartCmd = '/etc/init.d/apache2 graceful';
my $ApacheGracefulRestartCmd = '/etc/init.d/apache2 restart';

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

logwrite("Script not running as root, may fail.") unless $> == 0;
logwrite("Updating server list...");
# Read in list of servers.
open(my $Input, $ServerPath) or logdie("Unable to open servers.txt.\n");
while (my $Line = <$Input>) {
	next if $Line =~ m/^\s*$/;
	next if $Line =~ m/^\s*#/;
	die unless $Line =~ m/^\s*(.*?)\:?(\d+)?\s*$/;
	my $Host = $1;
	my $Port = $2;
	$Servers{$Host} = defined $Port ? $Port : 80;
}
close($Input);
# Make sure that they're all online.
my $Ping = Net::Ping->new();
foreach my $Server (keys(%Servers)) {
	next if $Ping->ping($Server);
	delete $Servers{$Server};
	logwrite("Server $Server is not responding to ping, not adding to list.");
}
$Ping->close();
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
my $Differ = 0;
if (stat($ConfigPath)->size == length($ConfigContents) ) {
	# Size is the same, let's do an md5 digest of our config, and the new one.
	my $ConfigCurrent = undef;
	{
		local $/;
		open($Input, $ConfigPath) or logdie("Unable to open configuration file for reading.");
		$ConfigCurrent = <$Input>;
		close($Input);
	}
	$Differ = 1 if (md5($ConfigContents) ne md5($ConfigCurrent));
}
else {
	$Differ = 1;
}

if ($Differ == 1) {	
	logwrite("Generated and current files differ. Replacing.");
	open(my $Output, ">", $ConfigPath) or logdie("Unable to open configuration file for writing.");
	print $Output $ConfigContents;
	close($Output);
	logwrite("Configuration updated sucessfully.");
	logwrite("Gracefully restarting apache.");
	# Perform a graceful restart.
	my $ApacheLog = `$ApacheGracefulRestartCmd`;
	if ($? != 0) {
		logwrite("Unable to gracefully restart apache: " . $ApacheLog);
		logwrite("Attempting full restart.");
		$ApacheLog = `$ApacheHardRestartCmd`;
		logdie("Unable to hard restart apache!") if $? != 0; 
	}
	logwrite("Succesfully restarted apache.");
}
else {
	logwrite("Config files identical. No rewrite.");
}

