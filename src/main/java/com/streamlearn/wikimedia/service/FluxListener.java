package com.streamlearn.wikimedia.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.streamlearn.wikimedia.config.ProducerConfig;
import com.streamlearn.wikimedia.model.RecentChange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


@Component
@Slf4j
public class FluxListener {


    @Autowired
    public ProducerConfig producerConfig;
    private final WebClient webClient;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    public FluxListener( WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("https://stream.wikimedia.org/v2").build();
    }

    private RecentChange generateRecentChangeEntity(String input)  {

        RecentChange recentChange = null;
        try {
            recentChange = objectMapper.readValue(input, RecentChange.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        return recentChange;
    }
    @Bean
    private Flux<String> prepareFluxStream(){
        return  webClient.get()
                .uri("/stream/recentchange")
                .accept(MediaType.ALL)
                .retrieve()
                .bodyToFlux(String.class);
    }
    public void recentChangeListenerFireAndForget(){
                Flux<String> responseStream = prepareFluxStream();
                responseStream.subscribe(response -> {
                    try {
                        RecentChange responseRecentChange = generateRecentChangeEntity(response);
                        producerConfig.publishRecentChangeFireAndForget(responseRecentChange);
                    } catch (JsonProcessingException e) {
                        log.error(response);
                    }
                },
                        error -> log.error("error occurred"+ error),
                        () -> log.info("Closed"));
    }
    public void recentChangeListenerSynchronous(){
        Flux<String> responseStream = prepareFluxStream();
        responseStream.subscribe(response -> {

                        RecentChange responseRecentChange = generateRecentChangeEntity(response);
                        producerConfig.publishRecentChangeSynchronous(responseRecentChange);
                },
                error -> log.error("error occurred"+ error),
                () -> log.info("Closed"));
    }

}
