package com.streamlearn.wikimedia.model;

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
    public int userid;
    public String img_sha1;
    public String img_timestamp;
    public String action;
    public String filter;
    public String actions;
    public int log;
    public Count count;
    public ArrayList<String> added;
    public ArrayList<String> removed;
    public String target;
    public String noredir;
    public String duration;
    public boolean sitewide;
    public String curid;
    public String previd;
    public int auto;
    public String expiry;
    public String description;
    public boolean cascade;
    public ArrayList<Detail> details;
    public String flags;
}
