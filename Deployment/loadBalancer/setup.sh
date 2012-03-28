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
export DEBIAN_FRONTEND=noninteractive
dpkg -i libapr1_1.4.5-1_amd64.deb
dpkg -i libaprutil1_1.3.12+dfsg-2_amd64.deb
dpkg -i libaprutil1-dbd-mysql_1.3.12+dfsg-2_amd64.deb
dpkg -i libaprutil1-ldap_1.3.12+dfsg-2_amd64.deb
dpkg -i apache2-utils_2.2.20-1ubuntu1.2_amd64.deb
dpkg -i apache2.2-bin_2.2.20-1ubuntu1.2_amd64.deb
dpkg -i apache2.2-common_2.2.20-1ubuntu1.2_amd64.deb
dpkg -i apache2-mpm-worker_2.2.20-1ubuntu1.2_amd64.deb

# Create symlinsk to load proper modules.
sudo ln -s $MOD_AVA/proxy.conf $MOD_ENA/proxy.conf
sudo ln -s $MOD_AVA/proxy.load $MOD_ENA/proxy.load

# Restart apache.
$APACHE_SCRIPT restart

exit 0
