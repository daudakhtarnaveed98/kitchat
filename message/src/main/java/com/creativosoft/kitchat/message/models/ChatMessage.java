package com.creativosoft.kitchat.message.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Type;
import org.jetbrains.annotations.Contract;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class ChatMessage {
    @Id
    private String id;

    @Column(nullable = false, name = "is_voice")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isVoice;

    @Column(nullable = false, name = "is_file")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isFile;

    @Lob
    private String text;
    private String fileName;
    private String fileType;
    private String createdAt;
    private String senderId;
    private String receiverId;
    private String groupId;

    @Contract(pure = true)
    public ChatMessage() {

    }

    @Contract(pure = true)
    public ChatMessage(String id, boolean isVoice, boolean isFile, String text, String fileName, String fileType, String createdAt, String senderId, String receiverId, String groupId) {
        this.id = id;
        this.isVoice = isVoice;
        this.isFile = isFile;
        this.text = text;
        this.fileName = fileName;
        this.fileType = fileType;
        this.createdAt = createdAt;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.groupId = groupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty(value = "isVoice")
    public boolean isVoice() {
        return isVoice;
    }

    public void setVoice(boolean voice) {
        isVoice = voice;
    }

    @JsonProperty(value = "isFile")
    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean file) {
        isFile = file;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}