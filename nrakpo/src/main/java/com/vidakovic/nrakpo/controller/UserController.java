package com.vidakovic.nrakpo.controller;

import com.vidakovic.nrakpo.aspect.Log;
import com.vidakovic.nrakpo.aspect.MeasureTime;
import com.vidakovic.nrakpo.controller.apimodel.UserApiModel;
import com.vidakovic.nrakpo.data.entity.enums.UserPackage;
import com.vidakovic.nrakpo.data.entity.enums.UserType;
import com.vidakovic.nrakpo.service.UserService;
import com.vidakovic.nrakpo.service.UserServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    UserService userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping()
    @MeasureTime(metricName = "get_users_timer")
    public String showUsers(Model model, Authentication authentication) {
        model.addAttribute("users", userService.getUsers());
        return "users";
    }

    @GetMapping("/update/{id}")
    public String showUpdateUser(Model model, @PathVariable(value = "id") String username, Authentication authentication) {
        model.addAttribute("user", userService.getUser(username));
        model.addAttribute("userPackages", UserPackage.values());
        model.addAttribute("userTypes", UserType.values());
        return "user_update";
    }

    @PostMapping("/update")
    @MeasureTime(metricName = "update_user_timer")
    @Log(message = "Updating user information")
    public String updateUser(@Valid @ModelAttribute UserApiModel user, Authentication authentication) {
        userService.updateUser(user);
        return "redirect:/home";
    }
}
