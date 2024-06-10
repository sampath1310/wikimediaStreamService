package com.streamlearn.wikimedia.consumer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@JsonIgnoreProperties
public class Count {
    public Long recent_change_id;
    public int revisions;
    public int files;
}
