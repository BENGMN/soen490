#!/bin/bash

# Shell script to install the balancer from a base debian distribution.
MAIN_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
UPDATE_SCRIPT=$MAIN_DIR/updater.pl
USERNAME=updateUser

# Base update.
apt-get update
# Install apache and necessary modules.
apt-get install perl
perl -MCPAN -e 'install Math::Pari'
perl -MCPAN -e 'install Net::SSH::Perl'
perl -MCPAN -e 'install Digest::MD5';
perl -MCPAN -e 'install Net::MySQL';
perl -MCPAN -e 'install Net::Ping';
adduser $USERNAME --disabled-password --gecos "" 
# Schedule updater script to run once every 10 mins.
# See http://en.wikipedia.org/wiki/Cron for details.
CRON_TIME='0,10,20,30,40,50 * * * *';
sudo $USERNAME crontab -l | { cat; echo "$CRON_TIME $UPDATE_SCRIPT"; } | crontab -
# Run update script.
sudo $USERNAME $UPDATE_SCRIPT

exit 0
