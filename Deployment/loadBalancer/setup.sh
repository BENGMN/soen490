#!/bin/bash

# Shell script to install the balancer from a base ubuntu distribution.

if [[ $EUID -ne 0 ]]; then
   echo "This script must be run as root." 1>&2
   exit 1
fi

CFG_DIR=/etc/apache2
APACHE_SCRIPT=/etc/init.d/apache2
MOD_ENA=$CFG_DIR/mods-enabled
MOD_AVA=$CFG_DIR/mods-available

# Install debs.
dpkg -i *.deb --assume-yes

# Create symlinsk to load proper modules.
sudo ln -s $MOD_AVA/proxy.conf $MOD_ENA/proxy.conf
sudo ln -s $MOD_AVA/proxy.load $MOD_ENA/proxy.load
# Restart apache.
$APACHE_SCRIPT restart

exit 0
