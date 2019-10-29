package com.creativosoft.kitchat.message.components;

import org.jetbrains.annotations.Contract;
import org.springframework.stereotype.Component;

@Component
public class Text {
    private String text;

    @Contract(pure = true)
    public Text() {

    }

    @Contract(pure = true)
    public Text(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
