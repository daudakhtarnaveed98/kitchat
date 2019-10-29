package com.creativosoft.kitchat.message.models;

import org.jetbrains.annotations.Contract;

import javax.persistence.*;

@Entity
public class PendingMessage {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int messageId;
    private String receiverEmailAddress;
    @Lob
    private String pendingMessage;

    @Contract(pure = true)
    public PendingMessage() {

    }

    @Contract(pure = true)
    public PendingMessage(String receiverEmailAddress, String pendingMessage) {
        this.receiverEmailAddress = receiverEmailAddress;
        this.pendingMessage = pendingMessage;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getReceiverEmailAddress() {
        return receiverEmailAddress;
    }

    public void setReceiverEmailAddress(String receiverEmailAddress) {
        this.receiverEmailAddress = receiverEmailAddress;
    }

    public String getPendingMessage() {
        return pendingMessage;
    }

    public void setPendingMessage(String pendingMessage) {
        this.pendingMessage = pendingMessage;
    }
}
