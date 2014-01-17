#!/bin/sh

. $(dirname $0)/env.sh

CLASSPATH=$CLASSPATH:$APP_HOME/bin/datafeed.jar

$JAVA_HOME/bin/java \
    -Xms256m -Xmx2048m \
    -Dlog4j.configuration=file://$APP_CONFIG/log4j.properties \
    -Djava.net.preferIPv4Stack=true \
    -Dfile.encoding=US-ASCII \
    -Dconfig=$APP_CONFIG/datafeed-server.properties \
    -classpath $CLASSPATH \
    com.frengky.onlinetrading.datafeed.app.Server

