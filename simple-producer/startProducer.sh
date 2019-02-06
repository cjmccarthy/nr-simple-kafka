#!/usr/bin/env bash

java -javaagent:target/newrelic.jar -Dnewrelic.config.file=./newrelic.yml \
  -Dcom.sun.management.jmxremote \
  -Dcom.sun.management.jmxremote.port=9010 \
  -Dcom.sun.management.jmxremote.local.only=false \
  -Dcom.sun.management.jmxremote.authenticate=false \
  -Dcom.sun.management.jmxremote.ssl=false \
  -cp "target/newrelic*" \
  -jar target/simple-producer-1.0-SNAPSHOT-jar-with-dependencies.jar 

