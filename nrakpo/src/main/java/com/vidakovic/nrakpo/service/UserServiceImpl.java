package com.vidakovic.nrakpo.service;

import com.vidakovic.nrakpo.controller.apimodel.UserApiModel;
import com.vidakovic.nrakpo.controller.form.RegistrationForm;
import com.vidakovic.nrakpo.data.entity.User;
import com.vidakovic.nrakpo.data.entity.enums.UserPackage;
import com.vidakovic.nrakpo.data.entity.enums.UserType;
import com.vidakovic.nrakpo.data.repository.PhotoRepository;
import com.vidakovic.nrakpo.data.repository.UserRepository;
import com.vidakovic.nrakpo.service.factory.UserFactory;
import com.vidakovic.nrakpo.service.strategy.PackageUsageService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private PackageUsageService packageUsageService;

    private PhotoRepository photoRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, PackageUsageService packageUsageService, PhotoRepository photoRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.packageUsageService = packageUsageService;
        this.photoRepository = photoRepository;
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

    public void checkMonthlyConsumption(String username) {
        User user = userRepository.findById(username).get();
        if(user.getUserType().equals(UserType.ADMINISTRATOR)){
            return;
        }
        long photoCount=photoRepository.countByUserAndDateBetween(user, Date.from(ZonedDateTime.now().minusMonths(1).toInstant()).getTime(), new Date().getTime());
        if(packageUsageService.exeededLimit(user, photoCount)){
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "You can't upload any more pictures this month");
        }
    }

}
