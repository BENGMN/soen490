#!/usr/bin/perl

use strict;
use warnings;

# Used to generate input for the tsung configuration files.
# First, we generate get request parameters.
my $MaxGetRequests = 100;
my $GetFile = "get.csv";
my $OUTPUTF; open($OUTPUTF, ">$GetFile") or die "Unable to open $GetFile.\n";
for (my $C = 0; $C < $MaxGetRequests; ++$C) {
	# Longitude First
	my $Longitude = rand(360) - 180.0;
	my $Latitude = rand(180) - 90.0;
	my $Speed = rand(100);
	print $OUTPUTF "$Longitude;$Latitude;$Speed\n";
}
close($OUTPUTF);

