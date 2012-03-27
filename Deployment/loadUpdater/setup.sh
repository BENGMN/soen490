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

# Install .debs.
dpkg -i *.deb --assume-yes
# Install self configured cpan.
cat no-cpan-config.pl | perl - --self-upgrade
# Have to configure things.

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
