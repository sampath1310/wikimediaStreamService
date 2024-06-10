package com.streamlearn.wikimedia.consumer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties
public class LogParams {
    public Long recent_change_id;
    public Long userid;
    public String img_sha1;
    public String img_timestamp;
    public String action;
    public String filter;
    public String actions;
    public Long log;
    public Count count;
    public ArrayList<String> added;
    public ArrayList<String> removed;
    public String target;
    public String noredir;
    public String duration;
    public boolean sitewide;
    public String curid;
    public String previd;
    public Long auto;
    public String expiry;
    public String description;
    public boolean cascade;
    public ArrayList<Detail> details;
    public String flags;
}
