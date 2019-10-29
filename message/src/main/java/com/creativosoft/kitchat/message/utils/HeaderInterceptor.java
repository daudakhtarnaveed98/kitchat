package com.creativosoft.kitchat.message.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HeaderInterceptor implements ClientHttpRequestInterceptor {
    private String headerName;
    private String headerValue;

    @Contract(pure = true)
    public HeaderInterceptor() {

    }

    @Contract(pure = true)
    public HeaderInterceptor(String headerName, String headerValue) {
        this.headerName = headerName;
        this.headerValue = headerValue;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getHeaderValue() {
        return headerValue;
    }

    public void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, @NotNull ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        HttpRequest wrapper = new HttpRequestWrapper(httpRequest);
        wrapper.getHeaders().set(headerName, headerValue);
        return clientHttpRequestExecution.execute(wrapper, bytes);
    }
}
