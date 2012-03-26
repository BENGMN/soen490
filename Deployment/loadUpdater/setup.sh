#!/bin/bash

# Installs the balancer updater on a particular machine.

if [[ $EUID -ne 0 ]]; then
   echo "This script must be run as root." 1>&2
   exit 1
fi

# Shell script to install the balancer updater from a base ubuntu distribution.
MAIN_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
UPDATE_SCRIPT=$MAIN_DIR/updater.pl
USERNAME=updateUser
OPTIONS=$@

# Base update.
apt-get update
# Install apache and necessary modules.
apt-get install perl curl --assume-yes
# Install self configured cpan.
curl -L http://cpanmin.us | perl - --self-upgrade
perl -MCPAN -e 'install Math::Pari'
perl -MCPAN -e 'install Net::SSH::Perl'
perl -MCPAN -e 'install Digest::MD5';
perl -MCPAN -e 'install Net::MySQL';
perl -MCPAN -e 'install Net::Ping';
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
