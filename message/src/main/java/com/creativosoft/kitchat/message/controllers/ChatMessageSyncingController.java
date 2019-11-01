package com.creativosoft.kitchat.message.controllers;

import com.creativosoft.kitchat.message.models.ChatMessage;
import com.creativosoft.kitchat.message.services.message.MessageSyncingService;
import org.jetbrains.annotations.Contract;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/sync")
public class ChatMessageSyncingController {
    private final MessageSyncingService syncingService;

    @Contract(pure = true)
    public ChatMessageSyncingController(MessageSyncingService syncingService) {
        this.syncingService = syncingService;
    }

    @RequestMapping(value = "/messages/{userEmailAddress}", method = RequestMethod.GET)
    public List<ChatMessage> getMessagesToSync(@PathVariable String userEmailAddress) {
        List<ChatMessage> messagesToSync = syncingService.getMessagesToSync(userEmailAddress);
        if (messagesToSync.size() > 0) {
            return syncingService.getMessagesToSync(userEmailAddress);
        }
        return messagesToSync;
    }
}