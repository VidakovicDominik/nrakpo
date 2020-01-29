package com.vidakovic.nrakpo.service;

import com.vidakovic.nrakpo.controller.apimodel.UserApiModel;
import com.vidakovic.nrakpo.controller.form.RegistrationForm;
import com.vidakovic.nrakpo.data.entity.User;
import com.vidakovic.nrakpo.data.entity.enums.UserPackage;
import com.vidakovic.nrakpo.data.entity.enums.UserType;
import com.vidakovic.nrakpo.data.repository.UserRepository;
import com.vidakovic.nrakpo.service.factory.UserFactory;
import com.vidakovic.nrakpo.service.strategy.PackageUsageService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private PackageUsageService packageUsageService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, PackageUsageService packageUsageService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.packageUsageService=packageUsageService;
    }

    public List<UserApiModel> getUsers(){
        return ((List<User>)userRepository.findAll()).stream().map(UserApiModel::new).collect(Collectors.toList());
    }

    public UserApiModel getUser(String username){
        return new UserApiModel(userRepository.findById(username).get());
    }

    public void updateUser(UserApiModel userApiModel){
        User user=userRepository.findById(userApiModel.getUsername()).get();
        user.setUserPackage(UserPackage.valueOf(userApiModel.getUserPackage()));
        userRepository.save(user);
    }

    public void insertUser(RegistrationForm registrationForm){
        User user=new UserFactory().getUser(registrationForm.getAccountType());
        if(registrationForm.getUsername().equals("admin")){
            user.setUserType(UserType.ADMINISTRATOR);
        }
        else{
            user.setUserType(UserType.USER);
        }
        user.setUsername(registrationForm.getUsername());
        user.setUserPackage(registrationForm.getUserPackage());
        user.setEmail(registrationForm.getEmail());
        user.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
        userRepository.saveUserWithAuthorities(user);
    }

    public boolean checkMonthlyConsumption(String username, long photoCount) {
        User user = userRepository.findById(username).get();
        if(user.getUserType().equals(UserType.ADMINISTRATOR)){
            return false;
        }
        return packageUsageService.exeededLimit(user, photoCount);
    }

}
