#!/bin/sh

. $(dirname $0)/env.sh

## CLASSPATH=$CLASSPATH:$HOME/build/Datafeed/dist/Datafeed.jar
CLASSPATH=$CLASSPATH:$HOME/bin/datafeed.jar
CONFIG_DATAFEED=$HOME/config/datafeed-client.properties

$JAVA_HOME/bin/java \
    -Xms32m -Xmx32m \
    -Dlog4j.configuration=file://$CONFIG_LOG4J \
    -Djava.net.preferIPv4Stack=true \
    -Dfile.encoding=US-ASCII \
    -Dconfig=$CONFIG_DATAFEED \
    -classpath $CLASSPATH \
    com.frengky.onlinetrading.datafeed.app.Client

