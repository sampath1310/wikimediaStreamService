package com.streamlearn.wikimedia.consumer;

import com.streamlearn.wikimedia.consumer.service.ConsumerService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaConsumerApplication {

    public static void main(String[] args) {

        ConsumerService kafkaConsumerApplication = new ConsumerService();
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaConsumerApplication::shutdown));
        kafkaConsumerApplication.start(kafkaConsumerApplication.keepConsuming);
    }


}
