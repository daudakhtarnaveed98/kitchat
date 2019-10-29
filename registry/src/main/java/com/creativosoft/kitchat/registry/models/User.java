package com.creativosoft.kitchat.registry.models;

import org.jetbrains.annotations.Contract;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    private String userEmailAddress;
    private String userPassword;
    @Basic
    private String deviceToken;

    @Contract(pure = true)
    public User() {

    }

    @Contract(pure = true)
    public User(String userEmailAddress, String userPassword, String deviceToken) {
        this.userEmailAddress = userEmailAddress;
        this.userPassword = userPassword;
        this.deviceToken = deviceToken;
    }

    public String getUserEmailAddress() {
        return userEmailAddress;
    }

    public void setUserEmailAddress(String userEmailAddress) {
        this.userEmailAddress = userEmailAddress;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}