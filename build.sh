#!/bin/sh

. $(dirname $0)/bin/env.sh

JAVA_HOME=$JAVA_HOME ant build
