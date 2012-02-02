#!/usr/bin/perl

use strict;
use warnings;
use threads;
use LWP::Simple;

my $DependencyFolder = "dep";
my $ConfigFileName = "$DependencyFolder/http_test_ericsson.xml";
my $DatabaseScript = "$DependencyFolder/sample.pl";
my $TsungInputScript = "$DependencyFolder/tsungInput.pl";
my $TsungStatsScript = "/usr/lib/tsung/bin/tsung_stats.pl";
my $DatabaseUsername = "soen490";
my $DatabaseName = "soen490";
my $DatabasePassword = "capstone";
my $ServerHostname = 'localhost';
my $ServerPort = 8080;
my $Webbrowser = 'google-chrome';

die "Must have $DatabaseScript in the folder.\n" unless (-e $DatabaseScript);
# Makes absolutely sure you want to run this script.
print "This script will erase your database!\nAre you sure you want to continue? [y/n]: ";
die "Aborting.\n" unless getc eq "y";
# We make sure tomcat's up on the server, or that we can connnect to it at least.
die "Tomcat must be up!" unless defined get("http://$ServerHostname:$ServerPort");
# Make sure we have various programs installed.
`tsung -v`; die "Tsung must be installed.\n" unless $? == 0;
`mysql --version`; die "Mysql client must be installed.\n" unless $? == 0;
`$TsungStatsScript --version`; die "tsung_stats must be installed.\n" unless $? == 0;
`$Webbrowser --version`; die "Must have a suitable webbrowser installed. (tried $Webbrowser)" unless $? == 0;

# Make sure we can access our configuration file.
die "Cannot access configuration file: $ConfigFileName.\n" unless (-e $ConfigFileName);

# Check to see if we have the right mysql password.
my @Tables = `mysql -u $DatabaseUsername --password=$DatabasePassword $DatabaseName -e 'SHOW TABLES'`;
die "Unable to access our mysql database. Wrong username/password?\n" unless $? == 0;
# Clear our database.
system("mysql -u $DatabaseUsername --password=$DatabasePassword $DatabaseName -e 'DELETE FROM User'") if grep(/User/, @Tables);
system("mysql -u $DatabaseUsername --password=$DatabasePassword $DatabaseName -e 'DELETE FROM Message'") if grep(/Message/, @Tables);
# Run our script to populate our database.
die "Trouble running database script.\n" unless system("perl $DatabaseScript | mysql -u $DatabaseUsername --password=$DatabasePassword $DatabaseName") == 0;

# Generate out tsung file inputs.
die "Trouble running tsung input script.\n" unless system("perl $TsungInputScript") == 0;

print "Setup complete... running tsung...\n";
my ($Second, $Minute, $Hour, $MDay, $Month, $Year, $WDay, $YDay, $DST) = localtime(time);
my $LogFolder = $ENV{"HOME"} . "/.tsung/log/" . sprintf("%4d%02d%02d-%02d%02d", ($Year+1900), ($Month+1), $MDay, $Hour, $Minute);
my $LogFile = "$LogFolder/tsung.log";
`tsung -f $ConfigFileName start`; die "Problem running tsung.\n" unless $? == 0;
die "Can't find logfile $LogFile...\n" unless -e $LogFile;
print "Generating report...\n";
chdir($LogFolder);
`$TsungStatsScript`; die "Unable to generate report.\n" unless $? == 0;
my $ReportFile = "$LogFolder/report.html";
die "Can't find report file.\n" unless -e $ReportFile;
print "Opening report...\n";
die "Can't open webbrowser.\n" unless system("$Webbrowser $ReportFile") == 0;
print "Done.\n";

