#!/usr/bin/env bash

java -javaagent:target/newrelic.jar \
  -cp "target/newrelic*" \
  -Dcom.sun.management.jmxremote \
  -Dcom.sun.management.jmxremote.port=9010 \
  -Dcom.sun.management.jmxremote.local.only=false \
  -Dcom.sun.management.jmxremote.authenticate=false \
  -Dcom.sun.management.jmxremote.ssl=false \
  -jar target/simple-consumer-1.0-SNAPSHOT-jar-with-dependencies.jar 

