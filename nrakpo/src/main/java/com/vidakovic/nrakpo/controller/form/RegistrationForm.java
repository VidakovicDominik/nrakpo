/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vidakovic.nrakpo.controller.form;

import com.vidakovic.nrakpo.controller.apimodel.LoggableApiModel;
import com.vidakovic.nrakpo.data.entity.enums.AccountType;
import com.vidakovic.nrakpo.data.entity.enums.UserPackage;
import com.vidakovic.nrakpo.data.entity.enums.UserType;


public class RegistrationForm  implements LoggableApiModel {

    private String username;

    private String password;

    private String email;

    private UserType userType= UserType.USER;

    private UserPackage userPackage;

    private AccountType accountType;

    
    public RegistrationForm() {
    }

    public RegistrationForm(String username, String password, String email, UserType userType, UserPackage userPackage, AccountType accountType) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userType = userType;
        this.userPackage = userPackage;
        this.accountType = accountType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UserPackage getUserPackage() {
        return userPackage;
    }

    public void setUserPackage(UserPackage userPackage) {
        this.userPackage = userPackage;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public String logText() {
        return "Received new user registration info -> Username:"+getUsername()+" User type:"+getUserType().toString();
    }
}
