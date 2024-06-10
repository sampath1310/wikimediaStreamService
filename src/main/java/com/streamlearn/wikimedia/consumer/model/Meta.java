package com.streamlearn.wikimedia.consumer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@JsonIgnoreProperties
public class Meta{
    public Long recent_change_id;
    public String uri;
    public String request_id;
    public String id;
    public String dt;
    public String domain;
    public String stream;
    public String topic;
    public int partition;
    public long offset;
}
