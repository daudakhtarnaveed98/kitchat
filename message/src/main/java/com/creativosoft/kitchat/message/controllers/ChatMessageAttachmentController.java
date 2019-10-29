package com.creativosoft.kitchat.message.controllers;

import com.creativosoft.kitchat.message.models.ChatMessageAttachment;
import com.creativosoft.kitchat.message.repositories.ChatMessageAttachmentRepository;
import org.jetbrains.annotations.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/attachment")
public class ChatMessageAttachmentController {
    private final ChatMessageAttachmentRepository chatMessageAttachmentRepository;

    @Contract(pure = true)
    @Autowired
    public ChatMessageAttachmentController(ChatMessageAttachmentRepository chatMessageAttachmentRepository) {
        this.chatMessageAttachmentRepository = chatMessageAttachmentRepository;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<Object> postAttachments(@RequestBody ChatMessageAttachment attachment) {
        chatMessageAttachmentRepository.save(attachment);
        return new ResponseEntity<>("Uploading complete.", HttpStatus.OK);
    }

    @RequestMapping(value = "/download/{fileId}", method = RequestMethod.GET)
    public Optional<ChatMessageAttachment> getAttachments(@PathVariable String fileId) {
        return chatMessageAttachmentRepository.findById(fileId);
    }
}