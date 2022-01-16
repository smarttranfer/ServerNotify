package com.tamzid.fcmdemo.controller;

import com.tamzid.fcmdemo.dto.NotificationRequestDto;
import com.tamzid.fcmdemo.dto.SubscriptionRequestDto;
import com.tamzid.fcmdemo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/subscribe")
    public void subscribeToTopic(@RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        notificationService.subscribeToTopic(subscriptionRequestDto);
    }

    @PostMapping("/unsubscribe")
    public void unsubscribeFromTopic(SubscriptionRequestDto subscriptionRequestDto) {
        notificationService.unsubscribeFromTopic(subscriptionRequestDto);
    }

    @PostMapping(value = "/Pushnotify",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendPns(@RequestBody NotificationRequestDto notificationRequestDto) throws JSONException {
        try{
            String resuslt = notificationService.sendPnsToDevice(notificationRequestDto);
            JSONObject resultNode = new JSONObject(String.format("{\"Status\":\"sucessful\", \"Content\":\"%s\"}","Send notify Done" ));
            return ResponseEntity.ok(resultNode.toString());
        }catch (Exception e){
            JSONObject resultNode = new JSONObject(String.format("{\"Status\":\"Fail\", \"Content\":\"%s\"}",e ));
            return ResponseEntity.ok(resultNode.toString());
        }

    }
    @PostMapping("/topic")
    public String sendPnsToTopic(@RequestBody NotificationRequestDto notificationRequestDto) {
        return notificationService.sendPnsToTopic(notificationRequestDto);
    }
}
