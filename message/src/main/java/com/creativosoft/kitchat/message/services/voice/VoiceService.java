package com.creativosoft.kitchat.message.services.voice;

import com.creativosoft.kitchat.message.components.Speech;
import com.creativosoft.kitchat.message.components.Text;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class VoiceService {
    private static String speechToTextServiceUrl = "http://3.19.218.245/speech";
    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;

    @Contract(pure = true)
    @Autowired
    public VoiceService(RestTemplate restTemplate, HttpHeaders httpHeaders) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
    }

    @Async("taskExecutor")
    public CompletableFuture<Text> convertVoiceToText(@NotNull Speech speech) {
        String encodedString = speech.getSpeech();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestPayload = new HashMap<>();
        requestPayload.put("speech", encodedString);

        HttpEntity<?> requestEntity = new HttpEntity<>(requestPayload, httpHeaders);
        ResponseEntity<Text> textResponseEntity = restTemplate.postForEntity(speechToTextServiceUrl, requestEntity, Text.class);

        return CompletableFuture.completedFuture(textResponseEntity.getBody());
    }

    @Contract(pure = true)
    public static String getSpeechToTextServiceUrl() {
        return speechToTextServiceUrl;
    }
}