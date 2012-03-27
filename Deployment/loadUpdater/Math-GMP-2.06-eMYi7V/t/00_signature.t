#!perl

## Test that our SIGNATURE file is valid

use 5.006;
use strict;
use warnings;
use Test::More;
select(($|=1,select(STDERR),$|=1)[1]);

if (! $ENV{RELEASE_TESTING}) {
	plan (skip_all =>  'Test skipped unless environment variable RELEASE_TESTING is set');
}
plan tests => 1;

SKIP: {
	if (!eval { require Module::Signature; 1 }) {
		skip ('Must have Module::Signature to test SIGNATURE file', 1);
	}
	elsif ( !-e 'SIGNATURE' ) {
		fail ('SIGNATURE file was not found');
	}
	elsif ( ! -s 'SIGNATURE') {
		fail ('SIGNATURE file was empty');
	}
	else {
		my $ret = Module::Signature::verify();
		if ($ret eq Module::Signature::SIGNATURE_OK()) {
			pass ('Valid SIGNATURE file');
		}
		else {
			fail ('Invalid SIGNATURE file');
		}
	}
}
