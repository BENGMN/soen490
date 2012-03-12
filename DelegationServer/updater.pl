#!/usr/bin/perl

# Updates the servers on the balancer with those that are active.
# Should be run occasionally so we can route around damaged servers.

use strict;
use warnings;
use LWP::Simple;
use Net::Ping;

my $Ping = Net::Ping->new();
my $BalancerURL = 'localhost/balancer-manager';
my $BalancerPage = get($BalancerURL);

sub formQueryString(%)
{
	my %Hash = shift;
	my $Query = "$BalancerURL?";
	foreach my $Key (keys(%Hash)) {
		$Query .= "$Key=" . uri_encode($Hash{$Key}) . "&";
	}
}

while (my $Line =~ m/<form method=\"GET\" action=\"\/balancer-manager\">(.*?)<\/form>/g) {
	my $Form = $1;
	my %Parameters = ();
	while ($Form =~ m/<input type=hidden name="(\w+)" value="(.*?)">/g) {
		$Parameters{$1} = $2;
	}
	my $Hostname = $Parameters{'w'};
	# We ping the server
	if ($Ping->($Hostname)) {
		get($BalancerURL . "?b=" . uriescape($Cluster) . "&w=" . uri_escape($Host) . "&nonce=" . $
	}
	else {
		
	}
}

$Ping->close();
