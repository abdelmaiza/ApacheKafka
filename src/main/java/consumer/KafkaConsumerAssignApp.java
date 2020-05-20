package consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KafkaConsumerAssignApp {
    public static void main (String [] args){
        KafkaConsumer<String, String> kafkaConsumer = KafkaConsumerUtils.getConfiguredConsumer(Collections.singletonList("192.168.0.14:9092"));
        List<TopicPartition> partitions = new ArrayList<>();
        partitions.add(new TopicPartition("my-topic",0));
        partitions.add(new TopicPartition("my-other-topic",2));

        kafkaConsumer.assign(partitions);

        try{
            while (true){
                final ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(10));
                for (ConsumerRecord<String, String> record : records ){
                    System.out.println(String.format("Topic: %s, Partition: %d, Offset: %d, Key: %s, Value: %s", record.topic(), record.partition(), record.offset(), record.key(), record.value()));
                }
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }finally {
            kafkaConsumer.close();
        }
    }
}
