package com.creativosoft.kitchat.message.services.notification;

import com.creativosoft.kitchat.message.utils.HeaderInterceptor;
import org.jetbrains.annotations.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Service
public class NotificationService {
    private final String FIREBASE_SERVER_KEY = "AAAAUBUlShM:APA91bG1F72DKA5WzHVy6nic0oRBkxJvp1uoVs_7_SNRUuKX9cOau8q27d2Z6-0KUP3TmSc0gxvlCq8AzgxMagSxIBw_fXjxoOEyjk-2tMUoJzgyOrAmo_72HXGTmc6VjGqiCYrKTPrZ";
    private final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
    private final RestTemplate restTemplate;

    @Contract(pure = true)
    @Autowired
    public NotificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    public CompletableFuture<String> sendNotificationToFirebase(HttpEntity<String> entity) {
        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);

        String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);
        return CompletableFuture.completedFuture(firebaseResponse);
    }

    public String getFIREBASE_SERVER_KEY() {
        return FIREBASE_SERVER_KEY;
    }

    public String getFIREBASE_API_URL() {
        return FIREBASE_API_URL;
    }
}