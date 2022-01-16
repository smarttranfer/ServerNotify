package com.tamzid.fcmdemo.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.tamzid.fcmdemo.dto.NotificationRequestDto;
import com.tamzid.fcmdemo.dto.SubscriptionRequestDto;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
@Service
public class NotificationService {

    @Value("${app.firebase-config}")
    private String firebaseConfig;

    private FirebaseApp firebaseApp;

    @PostConstruct
    private void initialize() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfig).getInputStream())).build();

            if (FirebaseApp.getApps().isEmpty()) {
                this.firebaseApp = FirebaseApp.initializeApp(options);
            } else {
                this.firebaseApp = FirebaseApp.getInstance();
            }
        } catch (IOException e) {
            log.error("Create FirebaseApp Error", e);
        }
    }

    public void subscribeToTopic(SubscriptionRequestDto subscriptionRequestDto) {
        try {
            FirebaseMessaging.getInstance(firebaseApp).subscribeToTopic(subscriptionRequestDto.getTokens(),
                    subscriptionRequestDto.getTopicName());
        } catch (FirebaseMessagingException e) {
            log.error("Firebase subscribe to topic fail", e);
        }
    }

    public void unsubscribeFromTopic(SubscriptionRequestDto subscriptionRequestDto) {
        try {
            FirebaseMessaging.getInstance(firebaseApp).unsubscribeFromTopic(subscriptionRequestDto.getTokens(),
                    subscriptionRequestDto.getTopicName());
        } catch (FirebaseMessagingException e) {
            log.error("Firebase unsubscribe from topic fail", e);
        }
    }

    public String sendPnsToDevice(NotificationRequestDto notificationRequestDto) {

        AndroidNotification androidNofi = AndroidNotification.builder()
                .setSound("default")
                .build();

        Message message = Message.builder()
                .setAndroidConfig(AndroidConfig.builder()
                        .setTtl(3600 * 1000)
                        .setNotification(AndroidNotification.builder()
                                .setColor("#D9AFD9")
                                .setTitle("BKAV Information")
                                .setSound(String.valueOf(androidNofi))
                                .build())
                        .build())
                .setToken("eWF5M7T67E32kTkJpIjF7z:APA91bFWwGJowGEnF1MMIS09p4Bz4ifiOjXjtWNlJk1jAHODO24I7huU8CIgXLeituFhj766CyDlGZghhbSWmZw-kjWYE1UHKqCU6xIV0NvWEIrXY6r7tRBW3Pl5RSK66hGQk4tymLgd")
                .setNotification(new Notification("CMSM INFORMATION " , "   Domain :"+ notificationRequestDto.getDomain()+"\n"+"   EventTime :" + notificationRequestDto.getEventtime() + "\n" +  "   Content :" + notificationRequestDto.getContent() + "\n" + "   Level :" + notificationRequestDto.getLevel()))
                .putData("content", notificationRequestDto.getDomain())
                .putData("body", (notificationRequestDto.getContent()))
                .build();

        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            log.error("Fail to send firebase notification", e);
        }
        return response;
    }

    public String sendPnsToTopic(NotificationRequestDto notificationRequestDto) {
        Message message = Message.builder()
                .setTopic(notificationRequestDto.getDomain())
                .setNotification(new Notification(notificationRequestDto.getDomain(), notificationRequestDto.getContent()))
                .putData("content", notificationRequestDto.getDomain())
                .putData("body", notificationRequestDto.getContent())
                .build();

        String response = null;
        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            log.error("Fail to send firebase notification", e);
        }

        return response;
    }
}
