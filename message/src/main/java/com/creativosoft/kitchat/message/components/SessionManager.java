package com.creativosoft.kitchat.message.components;

import org.jetbrains.annotations.Contract;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;

@Component
public class SessionManager {
    private static HashMap<String, WebSocketSession> socketSessionHashMap = new HashMap<>();
    private static HashMap<String, String> sessionIdHashMap = new HashMap<>();

    @Contract(pure = true)
    public SessionManager() {

    }

    @Contract(pure = true)
    public static HashMap<String, WebSocketSession> getSocketSessionHashMap() {
        return socketSessionHashMap;
    }

    @Contract(pure = true)
    public static HashMap<String, String> getSessionIdHashMap() {
        return sessionIdHashMap;
    }
}