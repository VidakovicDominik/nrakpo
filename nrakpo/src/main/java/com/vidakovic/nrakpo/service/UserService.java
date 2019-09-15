package com.vidakovic.nrakpo.service;

import com.vidakovic.nrakpo.controller.apimodel.UserApiModel;
import com.vidakovic.nrakpo.controller.form.RegistrationForm;
import com.vidakovic.nrakpo.data.entity.User;
import com.vidakovic.nrakpo.data.entity.enums.UserPackage;
import com.vidakovic.nrakpo.data.entity.enums.UserType;
import com.vidakovic.nrakpo.data.repository.UserRepository;
import com.vidakovic.nrakpo.service.factory.UserFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void updateUser(UserApiModel userApiModel){
        User user=userRepository.findById(userApiModel.getUsername()).get();
        user.setUserPackage(UserPackage.valueOf(userApiModel.getUserPackage()));
        user.setUserType(UserType.valueOf(userApiModel.getUserType()));
        userRepository.save(user);
    }

    public void insertUser(RegistrationForm registrationForm){
        User user=new UserFactory().getUser(registrationForm.getAccountType());
        if(registrationForm.getUsername().equals("admin")){
            user.setUserType(UserType.ADMINISTRATOR);
        }
        user.setUsername(registrationForm.getUsername());
        user.setUserPackage(registrationForm.getUserPackage());
        user.setEmail(registrationForm.getEmail());
        user.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
        userRepository.saveUserWithAuthorities(user);

    }

}
