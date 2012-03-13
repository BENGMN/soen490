#!/bin/bash

# Shell script to install the balancer from a base debian distribution.

CFG_DIR=/etc/apache2
RUN_SCRIPT=/etc/init.d/apache2
MOD_ENA=$CFG_DIR/mods-enabled
MOD_AVA=$CFG_DIR/mods-available
MAIN_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
UPDATE_SCRIPT=$MAIN_DIR/updater.pl

# Base update.
sudo apt-get update
# Install apache and necessary modules.
sudo apt-get install apache2
# Create symlinsk to load proper modules.
sudo ln -s $MOD_AVA/proxy.conf $MOD_ENA/proxy.conf
sudo ln -s $MOD_AVA/proxy.load $MOD_ENA/proxy.load
# Schedule updater script to run once every 10 mins.
# See http://en.wikipedia.org/wiki/Cron for details.
CRON_TIME='0,10,20,30,40,50 * * * *';
crontab -l | { cat; echo "$CRON_TIME $UPDATE_SCRIPT"; } | crontab -
# Run update script.
$UPDATE_SCRIPT
