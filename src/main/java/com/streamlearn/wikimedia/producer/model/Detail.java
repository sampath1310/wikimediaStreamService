package com.streamlearn.wikimedia.producer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties
public class Detail {
    public Long recent_change_id;
    public String type;
    public String level;
    public String expiry;
    public boolean cascade;
}
