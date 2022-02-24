package com.tamzid.fcmdemo.controller;

import com.tamzid.fcmdemo.dto.NotificationRequestDto;
import com.tamzid.fcmdemo.dto.SubscriptionRequestDto;
import com.tamzid.fcmdemo.service.NotificationService;
import com.tamzid.fcmdemo.service.ServiceToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    public ServiceToken servicetoken;

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
        String resuslts = "";
        try{
            for(String token :servicetoken.getoken_client()){
                String resuslt = notificationService.sendPnsToDevice(notificationRequestDto,token);
                resuslts = resuslt;
            }
            JSONObject resultNode = new JSONObject(String.format("{\"Status\":\"200\", \"Content\":\"%s\"}",resuslts ));
            return ResponseEntity.ok(resultNode.toString());


        }catch (Exception e){
            JSONObject resultNode = new JSONObject(String.format("{\"Status\":\"Fail\", \"Content\":\"%s\"}",e ));
            return ResponseEntity.ok(resultNode.toString());
        }
    }

    @PostMapping(value = "/UpdateToken",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> GetTokens(@RequestParam String token) throws JSONException {
        try{
            if( servicetoken.Settoken(token) == true){
                JSONObject resultNode = new JSONObject(String.format("{\"Status\":\"200\", \"Content\":\"%s\"}","Update token succesfull" ));
                return ResponseEntity.ok(resultNode.toString());
            }else {
                JSONObject resultNode = new JSONObject(String.format("{\"Status\":\"Fail\", \"Content\":\"%s\"}","Update token broken" ));
                return ResponseEntity.ok(resultNode.toString());
            }


        }catch (Exception e){
            JSONObject resultNode = new JSONObject(String.format("{\"Status\":\"Fail\", \"Content\":\"%s\"}",e ));
            return ResponseEntity.ok(resultNode.toString());
        }

    }


    @PostMapping(value = "/DeleteToken",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> Delete(@RequestParam String token) throws JSONException {
        try{
            if( servicetoken.deteleToken(token) == true){
                JSONObject resultNode = new JSONObject(String.format("{\"Status\":\"200\", \"Content\":\"%s\"}","Update token succesfull" ));
                return ResponseEntity.ok(resultNode.toString());
            }else {
                JSONObject resultNode = new JSONObject(String.format("{\"Status\":\"Fail\", \"Content\":\"%s\"}","Update token broken" ));
                return ResponseEntity.ok(resultNode.toString());
            }


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
