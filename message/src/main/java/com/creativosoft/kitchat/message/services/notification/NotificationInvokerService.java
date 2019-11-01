package com.creativosoft.kitchat.message.services.notification;

import com.creativosoft.kitchat.message.models.ChatMessage;
import com.creativosoft.kitchat.message.models.User;
import com.creativosoft.kitchat.message.repositories.UserRepository;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class NotificationInvokerService {
    private final NotificationService notificationsService;
    private final UserRepository userRepository;
    // TODO: Autowire these.
    private JSONObject body;
    private JSONObject notification;
    private JSONObject data;

    @Contract(pure = true)
    @Autowired
    public NotificationInvokerService(NotificationService notificationsService, UserRepository userRepository) {
        this.notificationsService = notificationsService;
        this.userRepository = userRepository;
        body = new JSONObject();
        notification = new JSONObject();
        data = new JSONObject();
    }

    public ResponseEntity<Object> sendNotification(@NotNull ChatMessage messageObject) {
        Optional<User> userOptional = userRepository.findById(messageObject.getReceiverId());
        String to = "";
        String from = messageObject.getSenderId();
        String text = "";

        if (messageObject.getText().length() > 32) {
            text = messageObject.getText().substring(0, 32);
        } else {
            text = messageObject.getText();
        }


        if (userOptional.isPresent()) {
            to = userOptional.get().getDeviceToken();
        }

        body.put("to", to);
        body.put("priority", "high");

        notification.put("title", from);
        if (messageObject.isFile()) {
            notification.put("body", " sent an attachment.");
        }
        else if (messageObject.isVoice()) {
            notification.put("body", " sent a voice message.");
        }
        else {
            notification.put("body", " sent: " + " " + text);
        }

        data.put("conversationId", from);
        body.put("notification", notification);
        body.put("data", data);
        body.put("android_channel_id", "main-channel");

        HttpEntity<String> request = new HttpEntity<>(body.toString());
        CompletableFuture<String> pushNotification = notificationsService.sendNotificationToFirebase(request);

        String firebaseResponse;
        try {
            firebaseResponse = pushNotification.get();
            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Push notification error", HttpStatus.BAD_REQUEST);
    }
}