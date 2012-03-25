#!/bin/bash

# Shell script to install the balancer from a base debian distribution.

CFG_DIR=/etc/apache2
APACHE_SCRIPT=/etc/init.d/apache2
MOD_ENA=$CFG_DIR/mods-enabled
MOD_AVA=$CFG_DIR/mods-available

# Base update.
sudo apt-get update
# Install apache and necessary modules.
sudo apt-get install apache2-mpm-worker
# Create symlinsk to load proper modules.
sudo ln -s $MOD_AVA/proxy.conf $MOD_ENA/proxy.conf
sudo ln -s $MOD_AVA/proxy.load $MOD_ENA/proxy.load
# Restart apache.
$APACHE_SCRIPT restart

exit 0
