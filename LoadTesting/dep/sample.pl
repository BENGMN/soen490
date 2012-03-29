#!/usr/bin/perl

use strict;
use warnings;
use String::Random qw(random_regex random_string);
use Data::Random qw(:all);
use POSIX qw(floor);
use POSIX qw(strftime);
use Digest::MD5 qw(md5_hex);
use bignum qw/hex/;

my $UsersToGenerate = 2000;
my $MessagesToGenerate = 10000;

my $AdvertiserChance = 0.1;
my $PasswordMinCharacters = 6;
my $PasswordMaxCharacters = 16;

my $TablePrefix = "application_";

print "DELETE FROM $TablePrefix" . "Message;\n";
print "DELETE FROM $TablePrefix" . "User;\n";

# GENERATE USERS
for (my $UID = 0; $UID < $UsersToGenerate; ++$UID) {
	my @EmailDomains = ("com", "net", "org");
	my $Email = random_regex('[a-z]{4,16}@[a-z]{4,16}') . "." . $EmailDomains[rand(@EmailDomains)];
	my $Password = random_regex('[a-zA-Z_0-9]' . "{$PasswordMinCharacters,$PasswordMaxCharacters}");
	my $UserType = ((rand(1.0) < $AdvertiserChance) ? 1 : 0);
	my $Version = int(rand(100));
	print "INSERT INTO $TablePrefix" . "User (uid, email, password, type, version) VALUES ($UID, '$Email', '$Password', $UserType, $Version);\n"
}

# GENERATE MESSAGES
# Min Size 1K, Max Size 20K.
my $MinSize = 1024;
my $MaxSize = 20480;

my $SpeedChance = 0.8;
my $MinRating = -100;
my $MaxRating = 200;

for (my $ID = 0; $ID < $MessagesToGenerate; ++$ID) {
	my $UID = int(rand($UsersToGenerate));
	my $MessageID = random_regex("[0-9]{39}");
	# Twice what we want because hexadecimal is twice the width of binary.
	my $MessageLength = floor((int(rand($MaxSize-$MinSize)) + $MinSize)/2)*4;
	my $Message = random_regex("[0-9A-F]{$MessageLength}");
	my $Speed = (rand(1.0) < $SpeedChance) ? rand(60) : undef;
	my $Latitude = rand(180.0) - 90.0;
	my $Longitude = rand(360.0) - 180.0;
	my $CreatedAt = rand_datetime(min => '2011-9-21 0:0:0');
	my $UserRating = int(rand($MaxRating - $MinRating)) + $MinRating;
	my $MID = hex(md5_hex($UID . $CreatedAt));
	print "INSERT INTO $TablePrefix" . "Message (mid, uid, message, speed, latitude, longitude, created_at, user_rating) VALUES ('$MID', $UID, UNHEX('$Message'), " . ((defined $Speed) ? $Speed : "NULL") . ", $Latitude, $Longitude, '$CreatedAt', $UserRating);\n";
}


