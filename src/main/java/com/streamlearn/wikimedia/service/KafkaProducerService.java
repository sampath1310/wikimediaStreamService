package com.streamlearn.wikimedia.service;

import com.streamlearn.wikimedia.config.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerService {

    @Autowired
    public ProducerConfig producerConfig;



}
