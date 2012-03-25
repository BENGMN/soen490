#!/usr/bin/perl

use strict;
use warnings;
use Net::SCP 'scp';
use Net::SSH::Perl;
use File::Basename;

my $RootPassword = $ARGV[0];
my $Target = $ARGV[1];
my $FilePath = $ARGV[2];

my($FileName, $Directories, $Suffic) = fileparse($FilePath);

die "Must be a zip archive." unless $Suffix eq "zip";
my $SetupFile = "setup.sh";

my $SCPE = Net::SCP::Expect->new;
$SCPE->login('root', $RootPassword);
die "Unable to scp file." unless $SCPE->scp($FilePath, "$Target:~/$FileName");
my $SSH = Net::SSH::Perl->new($Target);
die "Unable to SSH login." unless $SSH->login('root', $RootPassword);
my ($STDOUT, $STDERR, $EXIT) = $SSH->cmd("unzip ~/$FileName && ~/$SetupFile");

print "Successful installation.\n" if $EXIT == 0;
print "Error in installation, dumping logs: $STDOUT; $STDERR.\n" if $EXIT != 0;
