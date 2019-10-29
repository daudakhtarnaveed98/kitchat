package com.creativosoft.kitchat.message.services.attachment;

import com.creativosoft.kitchat.message.models.ChatMessage;
import com.creativosoft.kitchat.message.models.ChatMessageAttachment;
import com.creativosoft.kitchat.message.repositories.ChatMessageAttachmentRepository;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageAttachmentGetterService {
    // Attributes.
    private final ChatMessageAttachmentRepository chatMessageAttachmentRepository;

    @Contract(pure = true)
    @Autowired
    public MessageAttachmentGetterService(ChatMessageAttachmentRepository chatMessageAttachmentRepository) {
        this.chatMessageAttachmentRepository = chatMessageAttachmentRepository;
    }

    // Methods.
    public Optional<ChatMessageAttachment> getAttachment(@NotNull ChatMessage messageObj) {
        Optional<ChatMessageAttachment> attachment = chatMessageAttachmentRepository.findById(messageObj.getId());

        int i = 0;
        while (attachment.isEmpty() && i < 720) {
            attachment = chatMessageAttachmentRepository.findById(messageObj.getId());
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
        return attachment;
    }
}