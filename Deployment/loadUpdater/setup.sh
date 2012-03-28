#!/bin/bash

# Installs the balancer updater on a particular machine.

if [ $EUID -ne 0 ]; then
   echo "This script must be run as root." 1>&2
   exit 1
fi

# Shell script to install the balancer updater from a base ubuntu distribution.
MAIN_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
UPDATE_SCRIPT=$MAIN_DIR/updater.pl
USERNAME=updateUser
OPTIONS=$@

# Install Ubuntu Packages
dpkg -i libgmpxx4ldbl_5.0.1+dfsg-7ubuntu2_amd64.deb
dpkg -i libgmp-dev_5.0.1+dfsg-7ubuntu2_amd64.deb

# Install Perl Packages
cd Digest-MD5-2.51-YNh4OG
perl Makefile.PL
make && make install
cd ..
##################
cd Digest-SHA1-2.13-FjP6Eg
perl Makefile.PL
make && make install
cd ..
##################
cd Crypt-IDEA-1.08-Ljch8f
perl Makefile.PL
make && make install
cd ..
##################
cd Math-GMP-2.06-eMYi7V
perl Makefile.PL
make && make install
cd ..
##################
cd String-CRC32-1.4-P6ZVdw
perl Makefile.PL
make && make install
cd ..
##################
cd Math-Pari-2.01080605-Nmy9Vx
perl Makefile.PL pari_tgz=pari-2.3.4.tar.gz
make && make install
cd ..
##################
cd Net-SSH-Perl-1.34-QshJym
perl Makefile.PL
make && make install
cd ..
##################
cd Net-MySQL-0.11-SaGDeN
perl Makefile.PL
make && make install
cd ..
##################

# Install self configured cpan.
#cat no-cpan-config.pl | perl - --self-upgrade
# Add a new user to run the script (so we don't have to run as root everytime)
# With no password so we don't get an interactive prompt.
adduser $USERNAME --disabled-password --gecos "" 
# Schedule updater script to run once every 10 mins.
# See http://en.wikipedia.org/wiki/Cron for details.
CRON_TIME='0,10,20,30,40,50 * * * *';
sudo $USERNAME crontab -l | { cat; echo "$CRON_TIME $UPDATE_SCRIPT $OPTIONS"; } | crontab -
# Run update script.
sudo $USERNAME $UPDATE_SCRIPT $OPTIONS

exit 0
