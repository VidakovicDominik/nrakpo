package com.vidakovic.nrakpo.service;

import com.vidakovic.nrakpo.controller.apimodel.UserApiModel;
import com.vidakovic.nrakpo.controller.form.RegistrationForm;
import com.vidakovic.nrakpo.data.entity.LocalUser;
import com.vidakovic.nrakpo.data.entity.User;
import com.vidakovic.nrakpo.data.entity.enums.AccountType;
import com.vidakovic.nrakpo.data.entity.enums.UserPackage;
import com.vidakovic.nrakpo.data.entity.enums.UserType;
import com.vidakovic.nrakpo.service.strategy.PackageUsageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest extends ServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @MockBean
    PackageUsageService packageUsageService;

    @Test
    void getUsers() {
        List<User> users = Arrays.asList(createUser());
        when(userRepository.findAll()).thenReturn(users);
        List<UserApiModel> returnedUsers=userService.getUsers();

        assertThat(returnedUsers.iterator().next().getUsername()).isEqualTo(users.iterator().next().getUsername());
    }

    @Test
    void getUser() {
        Optional<User> user = Optional.of(createUser());
        when(userRepository.findById(any())).thenReturn(user);
        UserApiModel returnedUser=userService.getUser("username");

        assertThat(returnedUser.getUsername()).isEqualTo(user.get().getUsername());
    }

    @Test
    void updateUser() {
        Optional<User> user = Optional.of(createUser());
        when(userRepository.findById(any())).thenReturn(user);
        UserApiModel userApiModel = new UserApiModel();
        userApiModel.setUserPackage(UserPackage.FREE.toString());
        userService.updateUser(userApiModel);

        User expectedUser=createUser();
        expectedUser.setUserPackage(UserPackage.FREE);

        verify(userRepository).save(expectedUser);
    }

    @Test
    void insertUser() {
        User user= createUser();
        RegistrationForm registrationForm=new RegistrationForm(user.getUsername(), user.getPassword(), user.getEmail(), user.getUserType(), user.getUserPackage(), AccountType.LOCAL);

        userService.insertUser(registrationForm);

        verify(userRepository).saveUserWithAuthorities(any(LocalUser.class));
    }

    @Test
    void checkMonthlyConsumptionForUser() {
        when(userRepository.findById(any())).thenReturn(Optional.of(createUser()));
        when(packageUsageService.exeededLimit(any(),anyLong())).thenReturn(true);

        assertThat(userService.checkMonthlyConsumption("username",10));
    }

    @Test
    void checkMonthlyConsumptionForAdministrator() {
        User user = createUser();
        user.setUserType(UserType.ADMINISTRATOR);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(packageUsageService.exeededLimit(any(),anyLong())).thenReturn(true);

        assertThat(userService.checkMonthlyConsumption("username",10)).isFalse();
    }

    private User createUser() {
        return new User("user", "pass", "email", UserType.USER, UserPackage.GOLD);
    }
}
