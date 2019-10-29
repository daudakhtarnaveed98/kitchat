package com.creativosoft.kitchat.message.components;

import org.jetbrains.annotations.Contract;
import org.springframework.stereotype.Component;

@Component
public class Speech {
    private String speech;

    @Contract(pure = true)
    public Speech() {

    }

    @Contract(pure = true)
    public Speech(String speech) {
        this.speech = speech;
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }
}
