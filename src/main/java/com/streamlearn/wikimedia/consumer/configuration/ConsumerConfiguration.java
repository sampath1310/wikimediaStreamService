package com.streamlearn.wikimedia.consumer.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;

/**
 * The ConsumerConfiguration class sets up the configuration properties
 * required for a Kafka consumer. This includes essential properties such
 * as the bootstrap servers, deserializers, offset reset strategy, and
 * consumer group ID.
 */
public class ConsumerConfiguration {

    Properties consumerConfig =  new Properties();
    /**
     * Constructor that initializes the Kafka consumer configuration properties.
     * <p>
     * The following properties are set:
     * <ul>
     *     <li>{@link ConsumerConfig#BOOTSTRAP_SERVERS_CONFIG}: The Kafka server addresses</li>
     *     <li>{@link ConsumerConfig#KEY_DESERIALIZER_CLASS_CONFIG}: The class to deserialize keys</li>
     *     <li>{@link ConsumerConfig#VALUE_DESERIALIZER_CLASS_CONFIG}: The class to deserialize values</li>
     *     <li>{@link ConsumerConfig#AUTO_OFFSET_RESET_CONFIG}: The offset reset strategy</li>
     *     <li>{@link ConsumerConfig#GROUP_ID_CONFIG}: The consumer group ID</li>
     * </ul>
     */
    public ConsumerConfiguration(){

    }


    /**
     * Gets the Kafka consumer configuration properties.
     *
     * @return a {@link Properties} object containing the Kafka consumer configuration
     */
    public Properties getConsumerConfig(){
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG,"consumergroup1");

        return consumerConfig;
    }

    public KafkaConsumer createKafkaConsumer(){
        KafkaConsumer<String,String> kafkaConsumer=  new KafkaConsumer<>(getConsumerConfig());
        return kafkaConsumer;
    }
}
