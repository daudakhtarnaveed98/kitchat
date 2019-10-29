package com.creativosoft.kitchat.message.models;

import org.jetbrains.annotations.Contract;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class ChatMessageAttachment {
    @Id
    private String chatMessageAttachmentId;
    @Lob
    private String chatMessageAttachedFile;
    @Lob
    private String chatMessageAttachedSpeech;

    @Contract(pure = true)
    public ChatMessageAttachment() {

    }

    @Contract(pure = true)
    public ChatMessageAttachment(String chatMessageAttachmentId, String chatMessageAttachedFile, String chatMessageAttachedSpeech) {
        this.chatMessageAttachmentId = chatMessageAttachmentId;
        this.chatMessageAttachedFile = chatMessageAttachedFile;
        this.chatMessageAttachedSpeech = chatMessageAttachedSpeech;
    }

    public String getChatMessageAttachmentId() {
        return chatMessageAttachmentId;
    }

    public void setChatMessageAttachmentId(String chatMessageAttachmentId) {
        this.chatMessageAttachmentId = chatMessageAttachmentId;
    }

    public String getChatMessageAttachedFile() {
        return chatMessageAttachedFile;
    }

    public void setChatMessageAttachedFile(String chatMessageAttachedFile) {
        this.chatMessageAttachedFile = chatMessageAttachedFile;
    }

    public String getChatMessageAttachedSpeech() {
        return chatMessageAttachedSpeech;
    }

    public void setChatMessageAttachedSpeech(String chatMessageAttachedSpeech) {
        this.chatMessageAttachedSpeech = chatMessageAttachedSpeech;
    }
}
