package com.streamlearn.wikimedia.consumer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@JsonIgnoreProperties
public class Revision{
    public Long recent_change_id;
    public Long old;
    @JsonProperty("new")
    public Long mynew;
}
