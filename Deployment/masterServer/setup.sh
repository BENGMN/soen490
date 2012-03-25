#!/bin/sh

if [[ $EUID -ne 0 ]]; then
   echo "This script must be run as root." 1>&2
   exit 1
fi

FILE_NAME=masterServer.jar
TOMCAT_PATH=/var/lib/tomcat6/webapps
TOMCAT_UTIL=/etc/init.d/tomcat6
	
apt-get update
apt-get install apache2 tomcat6
mv $FILE_NAME $TOMCAT_PATH/
chown tomcat6 -R $TOMCAT_PATH
$TOMCAT_UTIL restart

exit 0
