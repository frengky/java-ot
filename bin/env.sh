#!/bin/sh

##
## Application Home Directory
##
HOME=/home/frengky/App/online-trading

##
## Java Environment
##
JAVA_HOME=/home/frengky/App/java-6-oracle-x86
CLASSPATH=$HOME/lib/'*'
CONFIG_LOG4J=$HOME/config/log4j.properties

##
## Apache ActiveMQ
##
ACTIVEMQ_HOME=$HOME/services/apache-activemq-5.6.0

export JAVA_HOME=$JAVA_HOME
export ACTIVEMQ_HOME=$ACTIVEMQ_HOME
