

# Test Kafka Consumer Service

## Overview
This project is used as playground for Kafka and Avro schema


## Useful Kafka commands to deal with topics and messages

List all topics
```
docker run --rm --network sda.net kafka-broker /var/kafka/kafka/bin/kafka-topics.sh --zookeeper kafka-zookeeper:2181 --list
```

Delete topic
```
docker run --rm --network sda.net kafka-broker /var/kafka/kafka/bin/kafka-topics.sh --zookeeper kafka-zookeeper:2181 --delete -topic test-event-topic
```

Cleanup topic and delete all messages
```
docker run --rm --network sda.net kafka-broker /var/kafka/kafka/bin/kafka-topics.sh --zookeeper kafka-zookeeper:2181 --alter -topic test-event-topic --config retention.ms=1000
```

Consumer for checking general messages on topic
```
docker run --rm --interactive --network sda.net kafka-broker /var/kafka/kafka/bin/kafka-console-consumer.sh --topic test-event-topic --from-beginning --bootstrap-server kafka-broker:9092
```
