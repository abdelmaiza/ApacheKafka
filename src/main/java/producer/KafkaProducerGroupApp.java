package producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaProducerGroupApp {
    public static void main(String[] args){
        String topic = "my-big-topic"; // name of topic
        int numberOfRecords = 100; // number of records to send
        // Create the Properties class to instantiate the Producer with the desired settings:
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.0.14:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");

        try (KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(props)) {
            int counter = 0;
            while (counter < numberOfRecords) {
                kafkaProducer.send(new ProducerRecord<>(topic, "abcdefghijklmnopqrstuvwxyz"));
                counter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
