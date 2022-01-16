package com.tamzid.fcmdemo.dto;

import lombok.Data;

import java.util.List;

@Data
public class NotificationRequestDto {
    private String domain;
    private String content;
    private String eventtype;
    private String eventtime;
    private String level;
    private List<String> userid;
}
