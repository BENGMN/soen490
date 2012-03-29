#!/usr/bin/perl

use strict;
use warnings;
use Net::MySQL;
use String::Random qw(random_regex random_string);

my @PossibleMessageIDs = ();
my @PossibleUserEmails = ();

die "Must pass in datbaase hostname as arg0." unless defined $ARGV[0];
die "Must pass in database password as arg0." unless defined $ARGV[1];
die "Must pass in datbaase username as arg0." unless defined $ARGV[2];
die "Must pass in database name as arg0." unless defined $ARGV[3];

my $DBHostname = $ARGV[0];
my $DBPassword = $ARGV[1];
my $DBUser = $ARGV[2];
my $DBName = $ARGV[3];
my $TablePrefix = "application_";

my $DB = Net::MySQL->new('hostname' => $DBHostname, 'database' => $DBName, 'user' => $DBUser, 'password' => $DBPassword);

my $Query = "SELECT mid FROM $TablePrefix" . "Message";
$DB->query($Query);
my $RecordSet = $DB->create_record_iterator;
die "Database error " . $DB->get_error_message if $DB->is_error;
print "Filtering message ids...\n";
while (my $Record = $RecordSet->each) {
	push(@PossibleMessageIDs, $Record->[0]);
}
$Query = "SELECT email FROM $TablePrefix" . "User";
$DB->query($Query);
$RecordSet = $DB->create_record_iterator;
die "Database error " . $DB->get_error_message if $DB->is_error;
print "Filtering user emails...\n";
while (my $Record = $RecordSet->each) {
	push(@PossibleUserEmails, $Record->[0]);
}



# Used to generate input for the tsung configuration files.
# First, we generate get request parameters.
my $MaxGetMessageIDsRequests = 100;
my $MaxReadMessageRequests = 100;
my $MaxUpvoteMessageRequests = 100;
my $MaxDownvoteMessageRequests = 100;
my $MaxPostMessageRequests = 100;
my $GetMessageIDsFile = "getmessageids.csv";
my $ReadMessageFile = "readmessage.csv";
my $UpvoteMessageFile = "upvotemessage.csv";
my $DownvoteMessageFile = "downvotemessage.csv";
my $PostMessageFile = "postmessage.csv";
my $OUTPUTF; open($OUTPUTF, ">$GetMessageIDsFile") or die "Unable to open $GetMessageIDsFile.\n";
print "Generating $GetMessageIDsFile...\n";
for (my $C = 0; $C < $MaxGetMessageIDsRequests; ++$C) {
	# Longitude First
	my $Longitude = rand(360) - 180.0;
	my $Latitude = rand(180) - 90.0;
	my $Speed = rand(100);
	print $OUTPUTF "$Longitude;$Latitude;$Speed\n";
}
close($OUTPUTF);
open($OUTPUTF, ">$ReadMessageFile") or die "Unable to open $ReadMessageFile.";
print "Generating $ReadMessageFile...\n";
for (my $C = 0; $C < $MaxReadMessageRequests; ++$C) {
	my @IDS = ();
	my $IDCount = int(rand(50))+1;
	for (my $D = 0; $D < $IDCount; ++$D) {
		push(@IDS, $PossibleMessageIDs[int(rand(int(@PossibleMessageIDs)))]);	
	}
	print $OUTPUTF "messageid=" . join("&messageid=", @IDS) . ";\n";
}
close($OUTPUTF);
open($OUTPUTF, ">$UpvoteMessageFile") or die "Unable to open $UpvoteMessageFile.";
print "Generating $UpvoteMessageFile...\n";
for (my $C = 0; $C < $MaxUpvoteMessageRequests; ++$C) {
	print $OUTPUTF $PossibleMessageIDs[int(rand(int(@PossibleMessageIDs)))] . ";\n";
}
close($OUTPUTF);
open($OUTPUTF, ">$DownvoteMessageFile") or die "Unable to open $DownvoteMessageFile.";
print "Generating $DownvoteMessageFile...\n";
for (my $C = 0; $C < $MaxDownvoteMessageRequests; ++$C) {
	print $OUTPUTF $PossibleMessageIDs[int(rand(int(@PossibleMessageIDs)))] . ";\n";
}
close($OUTPUTF);
open($OUTPUTF, ">$PostMessageFile") or die "Unable to open $PostMessageFile.";
print "Generating $PostMessageFile...\n";
for (my $C = 0; $C < $MaxPostMessageRequests; ++$C) {
	my $Longitude = rand(360.0) - 180;
	my $Latitude = rand(180.0) - 90.0;
	my $Speed = rand(100.0);
	my $MessageLength = int(rand(19000) + 1000);
	my $Bytes = random_regex("[0-9A-Za-z]{$MessageLength}");
	my $Email = $PossibleUserEmails[rand(@PossibleUserEmails)];
	print $OUTPUTF sprintf("%s;%.4f;%.4f;%.4f;%s\n", $Bytes, $Longitude, $Latitude, $Speed, $Email);
}
close($OUTPUTF);
