package com.creativosoft.kitchat.message.services.message;

import com.creativosoft.kitchat.message.models.ChatMessage;
import com.creativosoft.kitchat.message.repositories.ChatMessageRepository;
import com.creativosoft.kitchat.message.utils.Utils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Service
public class MessageSyncingService {
    // Attributes.
    private final ChatMessageRepository chatMessageRepository;
    private final Utils utils;

    @Contract(pure = true)
    @Autowired
    public MessageSyncingService(ChatMessageRepository chatMessageRepository, Utils utils) {
        this.chatMessageRepository = chatMessageRepository;
        this.utils = utils;
    }

    // Methods.
    void syncMessages(WebSocketSession syncSession, @NotNull ChatMessage messageObject) {
        List<ChatMessage> messagesToSync = chatMessageRepository.findMessagesToSync(messageObject.getSenderId());
        messagesToSync.sort(Comparator.comparing(ChatMessage::getCreatedAt));

        if (messagesToSync.size() > 0) {
            for (ChatMessage message : messagesToSync) {
                try {
                    syncSession.sendMessage(new TextMessage(utils.chatMessageToJson(message)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<ChatMessage> getMessagesToSync(String userEmailAddress) {
        return chatMessageRepository.findMessagesToSync(userEmailAddress);
    }
}