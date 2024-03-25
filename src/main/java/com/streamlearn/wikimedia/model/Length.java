package com.streamlearn.wikimedia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Getter
@Setter
@Slf4j
@JsonIgnoreProperties
public class Length {
    public int old;
    @JsonProperty("new")
    public int mynew;
}
