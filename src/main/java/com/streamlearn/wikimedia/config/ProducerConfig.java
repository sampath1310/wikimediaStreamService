package com.streamlearn.wikimedia.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.streamlearn.wikimedia.model.RecentChange;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.streamlearn.wikimedia.utils.Constant.RECENT_CHANGE_TOPIC;

@Component
@Slf4j
public class ProducerConfig{

    public final KafkaTemplate<String,String> kafkaTemplate;

    private  final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ProducerConfig(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public Map<String, Object> getKafkaTemplate(){return this.kafkaTemplate.getProducerFactory().getConfigurationProperties();}

    public void publishRecentChangeFireAndForget(RecentChange recentChange) throws JsonProcessingException {
        String jsonValue = objectMapper.writeValueAsString(recentChange);
        log.info("Recent Change with id: "+recentChange.id);
        kafkaTemplate.send(RECENT_CHANGE_TOPIC,jsonValue);
    }

    public void publishRecentChangeSynchronous(RecentChange recentChange) {

        String jsonValue = null;
        try {
            jsonValue = objectMapper.writeValueAsString(recentChange);
        } catch (JsonProcessingException e) {
           log.info("Failed to parse Json");
        }
        log.info("Recent Change with id: "+recentChange.id);
        try {
            SendResult<String, String> metadata =  kafkaTemplate.send(RECENT_CHANGE_TOPIC, jsonValue).get();
            RecordMetadata recordMetadata =  metadata.getRecordMetadata();
            log.info("Message is sent to Partition no " + recordMetadata.partition() + " and offset " + recordMetadata.offset());
            log.info("SynchronousProducer Completed with success.");
        }catch (InterruptedException interruptedException){
         log.error(interruptedException.getMessage());
         log.info("SynchronousProducer failed with an interruptedException");
         Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.info("SynchronousProducer failed with an ExecutionException");
        }
        finally {
            kafkaTemplate.flush();
            }
        }
}
