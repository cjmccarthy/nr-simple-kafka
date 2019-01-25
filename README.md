# nr-simple-kafka
Kafka producer/consumer pair with New Relic tracing


1: Follow the instructions to set up a local, one-node kafka cluster for testing: https://kafka.apache.org/quickstart

2: For each project, set your New Relic license key in newrelic.yml

3: mvn clean package

4: ./startProducer.sh

5: ./startConsumer.sh

6: Enjoy your distributed traces in 3-5 minutes
