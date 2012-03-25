#!/bin/sh

if [[ $EUID -ne 0 ]]; then
   echo "This script must be run as root." 1>&2
   exit 1
fi

FILE_NAME=masterServer.jar
JAR_URL=http://192.168.1.3/$FILE_NAME
TOMCAT_PATH=/var/lib/tomcat6/webapps
TOMCAT_UTIL=/etc/init.d/tomcat6
	
apt-get update
apt-get install apache2 tomcat6 wget
wget $JAR_URL
mv $FILE_NAME $TOMCAT_PATH/
chown tomcat6 -R $TOMCAT_PATH
$TOMCAT_UTIL restart

exit 0
