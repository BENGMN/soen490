#!/bin/sh

# Installs the master server on the specified server.

if [[ $EUID -ne 0 ]]; then
   echo "This script must be run as root." 1>&2
   exit 1
fi

FILE_NAME=masterServer.jar
TOMCAT_PATH=/var/lib/tomcat6/webapps
TOMCAT_UTIL=/etc/init.d/tomcat6

#Install .debs
dpkg -i *.deb --assume-yes	
# Move the .jar archive into the tomcat folder.
mv $FILE_NAME $TOMCAT_PATH/
# Make sure that tomcat can access the files we just moved in.
chown tomcat6 -R $TOMCAT_PATH
# Restart tomcat.
$TOMCAT_UTIL restart

exit 0
