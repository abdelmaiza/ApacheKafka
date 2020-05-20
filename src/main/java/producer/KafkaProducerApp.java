package producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;

public class KafkaProducerApp {
    public static void main(String[] args){
        String topic = "replicated-topic"; // name of topic
        int numberOfRecords = 100; // number of records to send
        long sleepTimer = 0; // how long you want to wait before the next record to be sent

        // Create the Properties class to instantiate the Producer with the desired settings:
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.0.14:9095,192.168.0.14:9096,192.168.0.14:9097");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");

        try (KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(props)) {
            int i = 0;
            while (i < numberOfRecords) {
                kafkaProducer.send(new ProducerRecord<>(topic, Integer.toString(i), "MyMessage " + i));
                i++;
            }
            Thread.sleep(sleepTimer);
            // Thread.sleep(new Random(5000).nextLong()); // use if you want to randomize the time between record sends
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
