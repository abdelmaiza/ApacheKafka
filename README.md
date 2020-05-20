# ApacheKafka
# one broker
docker-compose -f docker-compose-single-broker.yml up -d
docker exec -it kafka sh
cd /opt/kafka_2.12-2.4.1/
# bin/zookeeper-server-start.sh config/zookeeper.properties
# bin/kafka-topics.sh --create --topic my-topic --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1
bin/kafka-topics.sh --describe --topic my-topic --zookeeper zookeeper:2181
cd /kafka/kafka-logs-??????/my-topic-0
bin/kafka-topics.sh --list --zookeeper zookeeper:2181
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic my-topic
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic my-topic

# for test
bin/kafka-producer-perf-test.sh --topic my-topic --num-records 50 --record-size 1 --throughput 10 --producer-props bootstrap.servers=192.168.0.14:9092 key.serializer=org.apache.kafka.common.serialization.StringSerializer value.serializer=org.apache.kafka.common.serialization.StringSerializer
bin/kafka-producer-perf-test.sh --topic my-other-topic --num-records 50 --record-size 1 --throughput 10 --producer-props bootstrap.servers=192.168.0.14:9092 key.serializer=org.apache.kafka.common.serialization.StringSerializer value.serializer=org.apache.kafka.common.serialization.StringSerializer
bin/kafka-topics.sh --zookeeper zookeeper:2181 --alter --topic my-topic --partitions 4

# tree broker --partitions 3 --replication-factor 2
docker-compose up -d
docker exec -it apachekafka_kafka_1_1 sh
cd /opt/kafka_2.12-2.4.1/
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-topics.sh --zookeeper zookeeper:2181 --create --topic replicated-topic --partitions 3 --replication-factor 2
bin/kafka-topics.sh --list --zookeeper zookeeper:2181
bin/kafka-topics.sh --describe --topic replicated-topic --zookeeper zookeeper:2181
bin/kafka-console-producer.sh --broker-list 192.168.0.14:9095,192.168.0.14:9096,192.168.0.14:9097 --sync --topic zerg-hydra
bin/kafka-console-consumer.sh --bootstrap-server 192.168.0.14:9095 --topic zerg-hydra --from-beginning
bin/kafka-console-consumer.sh --bootstrap-server 192.168.0.14:9096 --topic zerg-hydra --from-beginning
bin/kafka-console-consumer.sh --bootstrap-server 192.168.0.14:9097 --topic zerg-hydra --from-beginning


# tree broker --partitions 1 --replication-factor 3
docker-compose up -d
docker exec -it kafka-1 sh
cd /opt/kafka_2.12-2.4.1/
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-topics.sh --zookeeper zookeeper:2181 --create --topic replicated-topic --partitions 1 --replication-factor 3
bin/kafka-topics.sh --list --zookeeper zookeeper:2181
bin/kafka-topics.sh --describe --topic replicated-topic --zookeeper zookeeper:2181
bin/kafka-console-producer.sh --broker-list 192.168.0.14:9095,192.168.0.14:9096,192.168.0.14:9097 --sync --topic replicated-topic
bin/kafka-console-consumer.sh --bootstrap-server 192.168.0.14:9095 --topic replicated-topic --from-beginning
bin/kafka-console-consumer.sh --bootstrap-server 192.168.0.14:9096 --topic replicated-topic --from-beginning
bin/kafka-console-consumer.sh --bootstrap-server 192.168.0.14:9097 --topic replicated-topic --from-beginning



