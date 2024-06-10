package com.streamlearn.wikimedia.consumer.service;

import com.google.gson.Gson;
import com.streamlearn.wikimedia.consumer.configuration.ConsumerConfiguration;
import com.streamlearn.wikimedia.consumer.model.RecentChange;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;

public class ConsumerService {

    ConsumerConfiguration consumerConfiguration = new ConsumerConfiguration();
    public volatile boolean keepConsuming = true;
    public final KafkaConsumer<String,String> kafkaConsumer =  consumerConfiguration.createKafkaConsumer();



    public void start(boolean keepConsuming){

        kafkaConsumer.subscribe(Collections.singleton("wikimedia-recent-change"));
        try {
            Gson gson = new Gson();
            while (keepConsuming) {

                ConsumerRecords<String,String> consumerRecords= kafkaConsumer.poll(Duration.ofSeconds(1));
                if(consumerRecords.count()<1){System.out.println("Consumer upto date");}
                consumerRecords.forEach(record -> {
                    System.out.println(record.value().replace("\\", ""));
                    System.out.println( "Records consumed is"+ gson.fromJson(record.value().replace("\\", ""), RecentChange.class));});
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(" Exception encountered");
        }finally {
            shutdown();
        }
    }

    public void shutdown(){
        try{
            if(kafkaConsumer != null){
                kafkaConsumer.wakeup();
                kafkaConsumer.close();
            }
        }catch (Exception exp){
            exp.printStackTrace();
        }finally {
            System.out.println("Kafka consumer closed sucessfully");
        }
    }

}
