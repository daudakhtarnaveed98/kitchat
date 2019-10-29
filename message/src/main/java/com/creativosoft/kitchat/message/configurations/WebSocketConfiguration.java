package com.creativosoft.kitchat.message.configurations;

import com.creativosoft.kitchat.message.services.message.MessageHandlingService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {
    private final MessageHandlingService messageHandlingService;

    @Contract(pure = true)
    @Autowired
    public WebSocketConfiguration(MessageHandlingService messageHandlingService) {
        this.messageHandlingService = messageHandlingService;
    }

    @Override
    public void registerWebSocketHandlers(@NotNull WebSocketHandlerRegistry registry) {
        registry.addHandler(messageHandlingService, "/message").setAllowedOrigins("*");
    }
}