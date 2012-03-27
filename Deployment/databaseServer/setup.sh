#!/bin/sh

if [ $EUID -ne 0 ]; then
	echo "Must run this script as root."
	exit -1;
fi

export DEBIAN_FRONTEND=noninteractive
dpkg -i *.deb --assume-yes
mysql -u root --password=$1
