#!/usr/bin/env bash

java -javaagent:target/newrelic.jar -cp "target/newrelic*" -jar target/simple-producer-1.0-SNAPSHOT-jar-with-dependencies.jar -Dnewrelic.config.file=./newrelic.yml 

