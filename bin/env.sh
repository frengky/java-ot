##
## Application Home Directory
##
APP_HOME=/home/frengky/App/online-trading
APP_CONFIG=$APP_HOME/config
##
## Java Environment
##
JAVA_HOME=/home/frengky/App/java-6-oracle-x86
CLASSPATH=$APP_HOME/lib/'*'
CONFIG_LOG4J=$APP_HOME/config/log4j.properties

##
## Apache ActiveMQ
##
ACTIVEMQ_HOME=$APP_HOME/services/apache-activemq-5.6.0

export JAVA_HOME=$JAVA_HOME
export ACTIVEMQ_HOME=$ACTIVEMQ_HOME
