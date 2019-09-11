package com.vidakovic.nrakpo.service;

import com.vidakovic.nrakpo.controller.apimodel.UserApiModel;
import com.vidakovic.nrakpo.data.entity.User;
import com.vidakovic.nrakpo.data.entity.enums.UserPackage;
import com.vidakovic.nrakpo.data.entity.enums.UserType;
import com.vidakovic.nrakpo.data.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updateUser(UserApiModel userApiModel){
        User user=userRepository.findById(userApiModel.getUsername()).get();
        user.setUserPackage(UserPackage.valueOf(userApiModel.getUserPackage()));
        user.setUserType(UserType.valueOf(userApiModel.getUserType()));
        userRepository.save(user);
    }
}
