#!/usr/bin/perl

use strict;
use warnings;
use Net::SCP::Expect;
use Net::SSH::Perl;
use File::Basename;
use Getopt::Long;

my $RootPassword = undef;
my $TargetHostname = undef;
my $Archive = undef;
my $DisplayHelp = undef;
my $ErrorMessage = undef;
my $Usage = "usage: $0 [ARCHIVE] [--help] [--password=pass]
[--hostname=URL]

	--help 		Displays this help text.
	--password	Specifies the root password
			for the deployment target.
	--hostname	Specifies the hostname for the
			deployment target.
";

GetOptions("password=s" => \$RootPassword, "hostname=s" => \$TargetHostname, '<>' => sub { $Archive = shift; });

if (defined $DisplayHelp) {
	print $Usage;
	exit 0;
}

$ErrorMessage = "Requires specified root password." unless defined $RootPassword;
$ErrorMessage = "Requires specified target hostname." unless defined $TargetHostname;
$ErrorMessage = "Requires a specified archive." unless defined $Archive;

if (defined $ErrorMessage) {
	print STDERR "Error: $ErrorMessage\n";
	print $Usage;
	exit -1;
}

my ($Name, $Directories, $Suffix) = fileparse($Archive, (".zip"));
die "$Archive must be a zip archive.\n" unless $Suffix eq ".zip";

my $SetupFile = "setup.sh";

my $SCPE = Net::SCP::Expect->new;
$SCPE->login('root', $RootPassword);
die "Unable to scp file." unless $SCPE->scp($Archive, "$TargetHostname:~/$Name.zip");
my $SSH = Net::SSH::Perl->new($TargetHostname);
die "Unable to SSH login." unless $SSH->login('root', $RootPassword);
my ($STDOUT, $STDERR, $EXIT) = $SSH->cmd("unzip ~/$Name && chmod +x ~/$SetupFile && ~/$SetupFile");

print "Successful installation.\n" if $EXIT == 0;
print "Error in installation, dumping logs: $STDOUT; $STDERR.\n" if $EXIT != 0;
