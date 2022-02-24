package com.tamzid.fcmdemo.dto;

import lombok.Data;

import java.util.List;

//@Data
public class NotificationRequestDto {
    private String domain;
    private String content;
    private String eventtype;
//    private String token;


//    public String getToken(){
//        return  token;
//    }
//
//    public void  setToken(String token){
//        this.token = token;
//    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEventtype() {
        return eventtype;
    }

    public void setEventtype(String eventtype) {
        this.eventtype = eventtype;
    }

    public String getEventtime() {
        return eventtime;
    }

    public void setEventtime(String eventtime) {
        this.eventtime = eventtime;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<String> getUserid() {
        return userid;
    }

    public void setUserid(List<String> userid) {
        this.userid = userid;
    }

    private String eventtime;
    private String level;
    private List<String> userid;
}
