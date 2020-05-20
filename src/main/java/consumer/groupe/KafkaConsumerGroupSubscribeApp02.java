package consumer.groupe;

import consumer.KafkaConsumerUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KafkaConsumerGroupSubscribeApp02 {
    public static void main(String [] args){
        KafkaConsumer<String, String> kafkaConsumer = KafkaConsumerUtils.getConfiguredConsumerGroup(Collections.singletonList("192.168.0.14:9092"),"order");
        List<String> topics = new ArrayList<>();
        topics.add("my-big-topic");

        kafkaConsumer.subscribe(topics);
        try{
            while (true){
                final ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(10));
                for (ConsumerRecord<String, String> record : records ){
                    System.out.println(String.format("Topic: %s, Partition: %d, Value: %s", record.topic(), record.partition(), record.value()));
                }
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }finally {
            kafkaConsumer.close();
        }
    }
}
