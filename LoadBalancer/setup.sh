#!/bin/sh

# Shell script to install the balancer from a base debian distribution.

CFG_DIR=/etc/apache2
RUN_SCRIPT=/etc/init.d/apache2
MOD_ENA=$CFG_DIR/mods-enabled
MOD_AVA=$CFG_DIR/mods-available

# Base update.
sudo apt-get update
# Install apache and necessary modules.
sudo apt-get install apache2
# Create symlinsk to load proper modules.
sudo ln -s $MOD_AVA/proxy_balancer.conf $MOD_ENA/proxy_balancer.conf 
sudo ln -s $MOD_AVA/proxy_balancer.load $MOD_ENA/proxy_balancer.load 
sudo ln -s $MOD_AVA/proxy.conf $MOD_ENA/proxy.conf
sudo ln -s $MOD_AVA/proxy.load $MOD_ENA/proxy.load
# Restart apache.
sudo $RUN_SCRIPT restart

