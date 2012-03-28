#!/bin/bash

if [ $EUID -ne 0 ]; then
	echo "Must run this script as root."
	exit -1;
fi

if [ $# -lt 1 ]; then
	echo "Must pass root password as first argument."
	exit -1;
fi

export DEBIAN_FRONTEND=noninteractive
# We need to do this in the proper order.
dpkg -i libnet-daemon-perl_0.48-1_all.deb
dpkg -i libdbi-perl_1.616-1build1_amd64.deb
dpkg -i libplrpc-perl_0.2020-2_all.deb
dpkg -i mysql-common_5.1.61-0ubuntu0.11.10.1_all.deb
dpkg -i libmysqlclient16_5.1.61-0ubuntu0.11.10.1_amd64.deb
dpkg -i libdbd-mysql-perl_4.019-1_amd64.deb
dpkg -i mysql-client-core-5.1_5.1.61-0ubuntu0.11.10.1_amd64.deb
dpkg -i mysql-client-5.1_5.1.61-0ubuntu0.11.10.1_amd64.deb
dpkg -i mysql-server-core-5.1_5.1.61-0ubuntu0.11.10.1_amd64.deb
dpkg -i mysql-server-5.1_5.1.61-0ubuntu0.11.10.1_amd64.deb
mysqladmin -u root password $1

exit 0
