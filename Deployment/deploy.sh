#!/usr/bin/perl

use strict;
use warnings;
use Net::SSH::Perl;
use Net::SCP::Expect;

die unless int(@ARGV) == 3;

my $SSHURL = $ARGV[0];
my $SSHUser = "root";
my $SSHPass = $ARGV[1];

my $SCPE = Net::SCP::Expect->new;
$SCPE->login($SSHUser, $SSHPass);
my $SetupPath = $ARGV[2];
$SetupPath =~ m//?([\w\.]+)$/;
my $SetupFile = $1; 
$SCPE->scp($SetupFile,"$SSHUrl:~/");
my $SSH = Net::SSH->new;

my $SSH = Net::SSH::Perl->new($SSHURL);
$SSH->login($SSHUser, $SSHPass);
my ($STDOUT, $STDERR, $EXIT) = $SSH->cmd("chmod +x ~/$SetupFile && ~/$SetupFile");

return $EXIT;
