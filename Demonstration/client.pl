#!/usr/bin/perl

use strict;
use warnings;
use LWP;
use URI;
use Data::MessagePack;

my $ServerHost = 'localhost';
my $ServerPort = 8080;
my $DatabaseUsername = 'soen490';
my $DatabaseName = 'soen490';
my $DatabasePassword = 'capstone';
my $DatabaseHostname = 'localhost';
my $DependencyFolder = "dep";
my $DatabaseScript = "$DependencyFolder/sample.pl";
my $ExternalBrowser = 'google-chrome';

my $Longitude = 10.0;
my $Latitude = 20.0;

`$ExternalBrowser --version`; die "You need to specify a browser that you have installed." unless $? == 0;

my $Browser = LWP::UserAgent->new;
$Browser->agent('Ericsson Http Client/1.0 (X11; U; Linux i686; en-US; rv:1.0.0)');
my $GetURL = URI->new("http://$ServerHost:$ServerPort/MasterServer/frontController");
$GetURL->query_form(
	'command' => "GetMessages",
	'longitude' => $Longitude,
	'latitude' => $Latitude,
);
print "Spinning up...\n";
print "Populating server through direct mysql injection...\n";
system("mysql -u $DatabaseUsername --password=$DatabasePassword $DatabaseName -h $DatabaseHostname -e 'DELETE FROM User'");
system("mysql -u $DatabaseUsername --password=$DatabasePassword $DatabaseName -h $DatabaseHostname -e 'DELETE FROM Message'");
# Run our script to populate our database.
die "Trouble running database script.\n" unless system("perl $DatabaseScript | mysql -u $DatabaseUsername --password=$DatabasePassword $DatabaseName -h$DatabaseHostname") == 0;
print "Sending GET request to $ServerHost at port $ServerPort. Full request is:\n";
print "$GetURL\n";
my $Response = $Browser->get($GetURL);
my $OUTPUT;
if (!$Response->is_success) {
	my $ResponseFile = "response.html";
	open($OUTPUT, ">$ResponseFile") or die ("Unable to open $ResponseFile.");
	print $OUTPUT $Response->content;
	system("$ExternalBrowser $ResponseFile");
	die "Unable to get a sucessful response: " . $Response->status_line . ".";
}
my $LengthInK = int(length($Response->content) / 1024);
print "Received a " . $Response->status_line . " response of " . length($Response->content) . " bytes ($LengthInK K):\n";
print "Parsing...\n";
my $Content = $Response->content;
open($OUTPUT, ">dump");
print $OUTPUT $Content;
close($OUTPUT);
# We have to hand it off to ruby here; the perl module isn't actually written properly; it throws an error.
system('ruby unpack.rb');
