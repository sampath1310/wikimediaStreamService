package com.streamlearn.wikimedia.producer.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.streamlearn.wikimedia.producer.model.Length;
import com.streamlearn.wikimedia.producer.model.Meta;
import com.streamlearn.wikimedia.producer.model.RecentChange;
import com.streamlearn.wikimedia.producer.model.Revision;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.streamlearn.wikimedia.producer.utils.Constant.*;

@Component
@Slf4j
public class ProducerConfig{

    public final KafkaTemplate<String,String> kafkaTemplate;

    private  final ObjectMapper objectMapper = new ObjectMapper();

    public ProducerConfig(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public Map<String, Object> getKafkaTemplate(){return this.kafkaTemplate.getProducerFactory().getConfigurationProperties();}

    public Map<String, String> objectSeperator(RecentChange recentChange) throws JsonProcessingException {

        Map<String, String> objectHolder = new HashMap<>();

        processObject(recentChange.length,"length",recentChange.id,objectHolder);
        processObject(recentChange.meta,"meta",recentChange.id,objectHolder);
        processObject(recentChange.revision,"revision",recentChange.id,objectHolder);
        String recent_change = objectMapper.writeValueAsString(recentChange);
        objectHolder.put("recent_change", objectMapper.writeValueAsString(recent_change));

        int length = recent_change.getBytes(StandardCharsets.UTF_8).length;
        log.info("Recent Change with id: "+recentChange.id+ "with size "+ length/125);
        return  objectHolder;
    }

    private <T> void processObject(T object,String key,Object id,Map map) throws JsonProcessingException {
        if(id!=null){
            if(object instanceof Length length){
                ((Length)object).recent_change_id = (Long) id;
                map.put(key,objectMapper.writeValueAsString((Length)object));
            }else if(object instanceof Meta meta){
                ((Meta)object).recent_change_id = (Long) id;
                map.put(key,objectMapper.writeValueAsString(object));
            }else if(object instanceof Revision revision){
                ((Revision)object).recent_change_id = (Long) id;
                map.put(key,objectMapper.writeValueAsString(object));

            }
        }
    }
    public void publishRecentChangeFireAndForget(RecentChange recentChange) throws JsonProcessingException {
        Map<String, String> publishObject = objectSeperator(recentChange);
        if(recentChange.length != null) {
            kafkaTemplate.send(RECENT_CHANGE_LENGTH, publishObject.get("length"));
        }
        if(recentChange.meta!=null){
        kafkaTemplate.send(RECENT_CHANGE_META, publishObject.get("meta"));}
        if(recentChange.revision!=null){
        kafkaTemplate.send(RECENT_CHANGE_REVISION, publishObject.get("revision"));}
        kafkaTemplate.send(RECENT_CHANGE_TOPIC, publishObject.get("recent_change"));

    }

    @Async
    public void publishRecentChangeAsynchronous(RecentChange recentChange) {
        try {
            String jsonValue = objectMapper.writeValueAsString(recentChange);
            int length = jsonValue.getBytes(StandardCharsets.UTF_8).length;
            log.info("Recent Change with id: "+recentChange.id+ "with size "+ (length/125));

            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(RECENT_CHANGE_TOPIC, jsonValue );
            future.thenApply( result -> {
                RecordMetadata recordMetadata =result.getRecordMetadata();
                log.info("Message is sent to Partition no " + recordMetadata.partition() + " and offset " + recordMetadata.offset());
                log.info("AsynchronousProducer Completed with success.");
                return null;
            }).exceptionally(err -> {  log.info("AsynchronousProducer failed with an ExecutionException");
                return null;
            });
        } catch (JsonProcessingException jsonProcessingException){log.error("JsonProcessingException parsing exception");}
          catch (Exception exception){ log.error("Failed at publishRecentChangeAsynchronous.");}
    }
    @Async
    public void publishRecentChangeAsynchronousStopOnError(RecentChange recentChange) {
        try {
            String jsonValue = objectMapper.writeValueAsString(recentChange);
            int length = jsonValue.getBytes(StandardCharsets.UTF_8).length;
            log.info("Recent Change with id: "+recentChange.id+ "with size "+ (length/125));

            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(RECENT_CHANGE_TOPIC, jsonValue );
            future.thenApply( result -> {
                RecordMetadata recordMetadata =result.getRecordMetadata();
                log.info("Message is sent to Partition no " + recordMetadata.partition() + " and offset " + recordMetadata.offset());
                log.info("AsynchronousProducer Completed with success.");
                return null;
            }).exceptionally(err -> {  log.info("AsynchronousProducer failed with an ExecutionException");
                throw new Error("Stop on Error Failure");
            });
        } catch (JsonProcessingException jsonProcessingException){log.error("JsonProcessingException parsing exception");throw new Error("Stop on Error Failure");}
        catch (Exception exception){ log.error("Failed at publishRecentChangeAsynchronous.");throw new Error("Stop on Error Failure");}
    }
    public void publishRecentChangeSynchronous(RecentChange recentChange) {
        String jsonValue = null;
        try {
            jsonValue = objectMapper.writeValueAsString(recentChange);
            int length = jsonValue.getBytes(StandardCharsets.UTF_8).length;
            log.info("Recent Change with id: "+recentChange.id+ "with size "+ (length/125));
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

    public void publishRecentChangeAsynchronousDeadLetterQueue(RecentChange recentChange) {
        try {
            String jsonValue = objectMapper.writeValueAsString(recentChange);
            int length = jsonValue.getBytes(StandardCharsets.UTF_8).length;
            log.info("Recent Change with id: "+recentChange.id+ "with size "+ (length/125));
            if(recentChange.id % 2 ==0){
//          Creating Exception to send data to error topic which can be omitted
                throw  new Exception("Failed with id:"+recentChange.id);
            }
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(RECENT_CHANGE_TOPIC, jsonValue );
            future.thenApply( result -> {
                RecordMetadata recordMetadata =result.getRecordMetadata();
                log.info("Message is sent to Partition no " + recordMetadata.partition() + " and offset " + recordMetadata.offset());
                log.info("AsynchronousProducer Completed with success.");
                return null;
            }).exceptionally(err -> {
                log.info("AsynchronousProducer failed with an ExecutionException");
                try {
                    kafkaTemplate.send(ERROR_TOPIC, err.getMessage());
                }catch (Exception exception){log.error("Failed to Publish to error topic");}
                return null;
            });
        } catch (JsonProcessingException jsonProcessingException){
            log.error("JsonProcessingException parsing exception");
            kafkaTemplate.send(ERROR_TOPIC,jsonProcessingException.getMessage());
        }
        catch (Exception exception){
            kafkaTemplate.send(ERROR_TOPIC,exception.getMessage());
            log.error("Failed at publishRecentChangeAsynchronous.");
        }
    }
}