package com.vidakovic.nrakpo.controller.apimodel;

import com.vidakovic.nrakpo.data.entity.User;

public class UserApiModel implements LoggableApiModel {

    private String username;

    private String email;

    private String userType;

    private String userPackage;

    private String accountType;

    public UserApiModel(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.userType = user.getUserType().toString();
        this.userPackage = user.getUserPackage().toString();
        this.accountType = user.getAccountType();
    }

    public UserApiModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserPackage() {
        return userPackage;
    }

    public void setUserPackage(String userPackage) {
        this.userPackage = userPackage;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String logText() {
        return "Received user data -> Username: "+this.username+" Email:"+ this.email+" User type:"+ userType;
    }
}
