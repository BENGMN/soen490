#!/usr/bin/perl

use strict;
use warnings;

my $ConfigFileName = "http_ericsson.xml";
my $DatabaseUsername = "soen490";
my $DatabaseName = "soen490";
my $DatabasePassword = "capstone";

# Makes absolutely sure you want to run this script.
print "This script will erase your database. Are you sure you want to continue? [y/n]: ";
die "Aborting." unless getc eq "y";
# We make sure that we're root.
die "You must run this script as root." unless $> == 0;
# We make sure tomcat's up.
my @Lines = `/etc/init.d/tomcat6 status`;
die "Tomcat must be up!" unless int(@Lines) == 1 && $Lines[0] =~ m/Tomcat servlet engine is running/;
# Make sure we have tsung installed.
@Lines = `tsung -v`;
die "Tsung must be installed." unless int(@Lines) == 1 && $Lines[0] =~ m/Tsung version \d+\.\d+\.\d+/;
# Make sure we can access our configuration file.
die "Cannot access configuration file: $ConfigFileName" unless (-e $ConfigFileName);
# Check to see if we have the right mysql password.
my @Tables = `mysql -u $DatabaseUsername --password=$DatabasePassword $DatabaseName -e 'SHOW TABLES'`;
die "Unable to access our mysql database. Wrong username/password?" unless $? == 0;
# Clear our database.
system("mysql -u $DatabaseUsername --password=$DatabasePassword $DatabaseName -e 'DELETE FROM Users'") if grep(/Users/, @Tables);
system("mysql -u $DatabaseUsername --password=$DatabasePassword $DatabaseName -e 'DELETE FROM Messages'") if grep(/Messages/, @Tables);
# Run our script to populate our database.

