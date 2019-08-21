/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vidakovic.nrakpo.controller.form;

import com.vidakovic.nrakpo.data.entity.enums.AccountType;
import com.vidakovic.nrakpo.data.entity.User;
import com.vidakovic.nrakpo.data.entity.enums.UserPackage;
import org.springframework.security.crypto.password.PasswordEncoder;


public class RegistrationForm {

    private String username;

    private String password;

    private String email;

    private UserType userType=UserType.USER;

    private UserPackage userPackage;

    private AccountType accountType;


    public User toUser(PasswordEncoder passwordEncoder){
        return new User(username, passwordEncoder.encode(password), email, userType, userPackage, accountType);
    }
    
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
}
