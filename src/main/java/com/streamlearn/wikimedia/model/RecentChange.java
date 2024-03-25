package com.streamlearn.wikimedia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Slf4j
@JsonIgnoreProperties
public class RecentChange {
    public String $schema;
    public Meta meta;
    public long id;
    public String type;
    public int namespace;
    public String title;
    public String title_url;
    public String comment;
    public int timestamp;
    public String user;
    public boolean bot;
    public String notify_url;
    public boolean minor;
    public boolean patrolled;
    public Length length;
    public Revision revision;
    public int log_id;
    public String log_type;
    public String log_action;
    public Object log_params;
    public String log_action_comment;
    public String server_url;
    public String server_name;
    public String server_script_path;
    public String wiki;
    public String parsedcomment;
}
