package com.tamzid.fcmdemo.dto;
import lombok.Data;
import java.util.List;

//@Data
public class SubscriptionRequestDto {

    String topicName;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }

    List<String> tokens;
}
