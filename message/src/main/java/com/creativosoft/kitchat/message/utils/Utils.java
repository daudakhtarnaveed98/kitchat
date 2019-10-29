package com.creativosoft.kitchat.message.utils;

import com.creativosoft.kitchat.message.models.ChatMessage;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.jetbrains.annotations.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.StringReader;

@Component
public class Utils {
    private final Gson gson;

    @Contract(pure = true)
    @Autowired
    public Utils(Gson gson) {
        this.gson = gson;
    }

    public ChatMessage jsonToChatMessage(String JsonToParse) {
        JsonReader reader = new JsonReader(new StringReader(JsonToParse));
        reader.setLenient(true);
        return gson.fromJson(reader, ChatMessage.class);
    }

    public String chatMessageToJson(ChatMessage chatMessage) {
        return gson.toJson(chatMessage);
    }
}