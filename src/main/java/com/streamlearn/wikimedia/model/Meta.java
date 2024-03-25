package com.streamlearn.wikimedia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Getter
@Setter
@Slf4j
@JsonIgnoreProperties
public class Meta{
    public String uri;
    public String request_id;
    public String id;
    public Date dt;
    public String domain;
    public String stream;
    public String topic;
    public int partition;
    public long offset;
}
