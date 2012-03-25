#!/usr/bin/perl

use strict;
use warnings;
use Net::SCP 'scp';
use Net::SSH::Perl;
use File::Basename;

my $RootPassword = $ARGV[0];
my $Target = $ARGV[1];
my $FilePath = $ARGV[2];

my $FileName = basename($FilePath);

my $SCPE = Net::SCP::Expect->new;
$SCPE->login('root', $RootPassword);
die "Unable to scp file." unless $SCPE->scp($FilePath, "$Target:~/$FileName");
my $SSH = Net::SSH::Perl->new($Target);
die "Unable to SSH login." unless $SSH->login('root', $RootPassword);

my ($STDOUT, $STDERR, $EXIT) = $SSH->cmd("chmod +x ~/$FileName && ~/$FileName");

print "Successful installation.\n" if $EXIT == 0;
print "Error in installation, dumping logs: $STDOUT; $STDERR.\n" if $EXIT != 0;
