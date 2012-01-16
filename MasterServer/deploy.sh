NAME=MasterServer
DEPLOY_PATH=/var/lib/tomcat6/webapps
SERVER_PATH=$DEPLOY_PATH/$NAME
CONFIG_FILE=Database.properties
OFFENDING_LIBS=$DEPLOY_PATH/$NAME/WEB-INF/lib/servlet-api.jar

if [[ $EUID -ne 0 ]]; then
   echo "This script must be run as root." 1>&2
   exit 1
fi

mkdir -p $SERVER_PATH
cp -R WebContent/* $SERVER_PATH
mkdir -p $SERVER_PATH/WEB-INF/classes
cp -R lib $SERVER_PATH/WEB-INF
cp -R bin/* $SERVER_PATH/WEB-INF/classes
cp Database.properties $SERVER_PATH/WEB-INF/classes/
chown tomcat6 -R $SERVER_PATH
rm $OFFENDING_LIBS
/etc/init.d/tomcat6 restart
