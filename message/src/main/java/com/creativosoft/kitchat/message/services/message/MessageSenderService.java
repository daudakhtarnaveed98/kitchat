package com.creativosoft.kitchat.message.services.message;

import com.creativosoft.kitchat.message.models.ChatMessage;
import com.creativosoft.kitchat.message.models.PendingMessage;
import com.creativosoft.kitchat.message.repositories.ChatMessageRepository;
import com.creativosoft.kitchat.message.repositories.PendingMessageRepository;
import com.creativosoft.kitchat.message.services.notification.NotificationInvokerService;
import com.creativosoft.kitchat.message.utils.Utils;
import org.jetbrains.annotations.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Service
public class MessageSenderService {
    private final ChatMessageRepository chatMessageRepository;
    private final PendingMessageRepository pendingMessageRepository;
    private final Utils utils;
    private final NotificationInvokerService invokerService;

    @Contract(pure = true)
    @Autowired
    public MessageSenderService(ChatMessageRepository chatMessageRepository, PendingMessageRepository pendingMessageRepository, Utils utils, NotificationInvokerService invokerService) {
        this.chatMessageRepository = chatMessageRepository;
        this.pendingMessageRepository = pendingMessageRepository;
        this.utils = utils;
        this.invokerService = invokerService;
    }

    public void sendMessage(WebSocketSession receiverSession, ChatMessage messageObject) {
        String messageObjectJson = utils.chatMessageToJson(messageObject);

        if (receiverSession != null) {

            try {
                receiverSession.sendMessage(new TextMessage(messageObjectJson));
                //TODO: Unused variable.
                ResponseEntity<Object> objectResponseEntity = invokerService.sendNotification(messageObject);
            } catch (IOException e) {
                e.printStackTrace();
            }
            chatMessageRepository.save(messageObject);
        }
        else {
            pendingMessageRepository.save(new PendingMessage(messageObject.getReceiverId(), messageObjectJson));
        }
    }
}