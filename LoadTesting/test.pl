#!/usr/bin/perl

use strict;
use warnings;
use threads;
use LWP::Simple;
use Getopt::Long;

my $DependencyFolder = "dep";
my $ConfigFileName = undef;
my $DatabaseScript = "$DependencyFolder/sample.pl";
my $TsungInputScript = "$DependencyFolder/tsungInput.pl";
my $TsungStatsScript = "/usr/lib/tsung/bin/tsung_stats.pl";
my $DatabaseUsername = "soen490";
my $DatabaseName = "soen490";
my $DatabasePassword = "capstone";
my $DatabaseHostname = undef;
my $ServerHostname = 'localhost';
my $ServerPort = 8080;
my $Webbrowser = 'google-chrome';
my $Verbose = undef;
my $Quiet = undef;
my $DisplayHelp = undef;
my $NoReport = undef;
my $DieAtStart = undef;

my $HelpMessage = "
Garry's Load Testing Script

usage: $0 configuration_file [--help] [--verbose|--quiet]
[--server=localhost] [--port=8080] [--dname=soen490] [--duser==soen490]
[-dpass=capstone] [--dhost=--server] [--browser=google-chrome] [--noreport]

This is the load testing script, written by Garry. It basically does a
preliminary check to see if you have all the appropriate programs, and
then generates an appropriate database for tsung to work with, on the
server, calls tsung, and finally generates an html report, using
tsung's internal tools. It has the following options:

	--help		Display this help information.
	--verbose	Display all information coming out of the script. No
			peace and quiet. Useful for debugging.
	--quiet		Output nothing at all.
	--server	Specifies the server to connect to. Default is
			localhost.
	--port		Specifies the port to connec to the server on. Default
			is 8080.
	--dname		Specifies database name. Default is soen490.
	--duser		Specifies database username. Default is soen490.
	--dpass		Specifies duser password. Default is capstone.
	--dhost		Specifies the hostname for the database server.
			Default is whatever's been specified for server.
	--browser	Specifies the browser to use. Default is
			google-chrome.
	--noreport	Just runs tsung; doesn't bother with the report
			generating step.\n";
GetOptions("help" => \$DisplayHelp, "verbose" => \$Verbose, "quiet" => \$Quiet, "browser=s" => \$Webbrowser, "server=s" => \$ServerHostname,
"port=i" => \$ServerPort, "dname=s" => \$DatabaseUsername, "dpass=s" => \$DatabasePassword, "dname=s" => \$DatabaseName, "dhost=s" => \$DatabaseHostname, "noreport" => \$NoReport,
'<>' => sub { $DieAtStart = "Can only specify one configuration file.\n" if (defined $ConfigFileName); $ConfigFileName = shift; });

$DieAtStart = "Can't define both quiet and verbose.\n" if defined $Quiet && defined $Verbose;
die $DieAtStart if defined $DieAtStart;

$DatabaseHostname = $ServerHostname unless defined $DatabaseHostname;
$DisplayHelp = 1 unless defined $ConfigFileName;

if (defined $DisplayHelp) {
	print $HelpMessage;
	exit(0);
}

sub run($) {
	my $Program = shift;
	if (defined $Verbose) {
		print "$Program\n";
		return system($Program);
	}
	`$Program 2>/dev/null`; return $?;
}

if ($^O ne "linux") {
	print "I literally have no idea what will happen if you don't run this on linux.\n";
	print "You have been warned. Things could end up getting deleted.\n";
	print "Are you sure you want to contiunue? [y/n]: ";
	die "Aborting.\n" unless getc eq "y"; <STDIN>
}

print "Checking installed programs...\n" unless defined $Quiet;
# We make sure tomcat's up on the server, or that we can connnect to it at least.
die "Tomcat must be up!" unless defined get("http://$ServerHostname:$ServerPort");
# Make sure we have various programs installed.
die "Tsung must be installed.\n" unless run("tsung -v") == 0;
die "Mysql client must be installed.\n" unless run("mysql --version") == 0;
die "tsung_stats must be installed.\n" unless run("$TsungStatsScript --version") == 0;
die "Must have a suitable webbrowser installed. (tried $Webbrowser)" unless defined $NoReport || run("$Webbrowser --version") == 0;

# Make sure we can access our configuration file.
print "Checking configuration file...\n" unless defined $Quiet;
die "Cannot find configuration file: $ConfigFileName.\n" unless (-e $ConfigFileName);

die "Must have $DatabaseScript.\n" unless (-e $DatabaseScript);
# Makes absolutely sure you want to run this script.
print "This script will erase the database on $DatabaseHostname!\nAre you sure you want to continue? [y/n]: ";
die "Aborting.\n" unless getc eq "y"; <STDIN>;

# Check to see if we have the right mysql password.
print "Accessing mysql database; deleting current rows...\n" unless defined $Quiet;
my @Tables = `mysql -u $DatabaseUsername --password=$DatabasePassword $DatabaseName -e 'SHOW TABLES'`;
die "Unable to access our mysql database. Wrong username/password?\n" unless $? == 0;
# Clear our database.
die "Mysql database must be set up properly (have User and Message); run a unit test or something." unless grep(/User/, @Tables) && grep(/Message/, @Tables);
run("mysql -u $DatabaseUsername --password=$DatabasePassword $DatabaseName -h $DatabaseHostname -e 'DELETE FROM User'");
run("mysql -u $DatabaseUsername --password=$DatabasePassword $DatabaseName -h $DatabaseHostname -e 'DELETE FROM Message'");
# Run our script to populate our database.
die "Trouble running database script.\n" unless run("perl $DatabaseScript | mysql -u $DatabaseUsername --password=$DatabasePassword $DatabaseName -h $DatabaseHostname") == 0;

# Generate out tsung file inputs.
print "Generating tsung inputs...\n" unless defined $Quiet;
die "Trouble running tsung input script.\n" unless run("perl $TsungInputScript") == 0;

# Actually run tsung.
print "Setup complete... running tsung...\n" unless defined $Quiet;
my ($Second, $Minute, $Hour, $MDay, $Month, $Year, $WDay, $YDay, $DST) = localtime(time);
my $LogFolder = $ENV{"HOME"} . "/.tsung/log/" . sprintf("%4d%02d%02d-%02d%02d", ($Year+1900), ($Month+1), $MDay, $Hour, $Minute);
my $LogFile = "$LogFolder/tsung.log";
die "Problem running tsung.\n" unless run("tsung -f $ConfigFileName start") == 0;

if (!defined $NoReport) {
	# Generate our reports.
	print "Generating report...\n" unless defined $Quiet;
	die "Can't find logfile $LogFile...\n" unless -e $LogFile;
	die "Cannot change folder to $LogFolder." unless chdir($LogFolder);
	die "Unable to generate report.\n" unless run("$TsungStatsScript") == 0;
	my $ReportFile = "$LogFolder/report.html";
	die "Can't find report file.\n" unless -e $ReportFile;
	print "Opening report...\n" unless defined $Quiet;
	die "Can't open webbrowser.\n" unless run("$Webbrowser $ReportFile") == 0;
}
print "Done.\n" unless defined $Quiet;

