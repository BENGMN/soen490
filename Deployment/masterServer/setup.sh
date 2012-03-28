#!/bin/bash

# Installs the master server on the specified server.

if [ $EUID -ne 0 ]; then
	echo "Must run this script as root."
	exit -1;
fi


FILE_NAME=masterServer.jar
TOMCAT_PATH=/var/lib/tomcat6/webapps
TOMCAT_UTIL=/etc/init.d/tomcat6

#Install .debs
export DEBIAN_FRONTEND=noninteractive
dpkg -i java-common_0.42ubuntu2_all.deb
dpkg -i libecj-java_3.5.1-3_all.deb
dpkg -i libcommons-pool-java_1.5.6-1_all.deb
dpkg -i libcommons-collections3-java_3.2.1-5_all.deb
dpkg -i tzdata-java_2012b-0ubuntu0.11.10_all.deb
dpkg -i libservlet2.5-java_6.0.32-5ubuntu1.2_all.deb
dpkg -i ca-certificates-java_20110912ubuntu3_all.deb
dpkg -i openjdk-6-jre-headless_6b23~pre11-0ubuntu1.11.10.2_amd64.deb
dpkg -i libnss3-1d_3.12.9+ckbi-1.82-0ubuntu6_amd64.deb
dpkg -i openjdk-6-jre-lib_6b23~pre11-0ubuntu1.11.10.2_all.deb
dpkg -i default-jre-headless_1.6-42ubuntu2_amd64.deb
dpkg -i libcommons-dbcp-java_1.4-1ubuntu1_all.deb
dpkg -i libtomcat6-java
dpkg -i tomcat6-common_6.0.32-5ubuntu1.2_all.deb
dpkg -i tomcat6_6.0.32-5ubuntu1.2_all.deb

# Move the .jar archive into the tomcat folder.
mv $FILE_NAME $TOMCAT_PATH/
# Make sure that tomcat can access the files we just moved in.
chown tomcat6 -R $TOMCAT_PATH
# Restart tomcat.
$TOMCAT_UTIL restart

exit 0
