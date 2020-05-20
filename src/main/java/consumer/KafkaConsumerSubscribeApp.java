package consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

public class KafkaConsumerSubscribeApp {
    public static void main (String [] args){

        KafkaConsumer<String, String> kafkaConsumer = KafkaConsumerUtils.getConfiguredConsumer(Collections.singletonList("192.168.0.14:9092"));
        kafkaConsumer.subscribe(Arrays.asList("my-topic","my-other-topic"));
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
