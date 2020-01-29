package com.vidakovic.nrakpo.controller;

import com.vidakovic.nrakpo.controller.apimodel.UserApiModel;
import com.vidakovic.nrakpo.data.entity.enums.UserPackage;
import com.vidakovic.nrakpo.data.entity.enums.UserType;
import com.vidakovic.nrakpo.service.UserService;
import com.vidakovic.nrakpo.service.singleton.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String showIndex(Model model , Authentication authentication) {
        Logger.getInstance().log(authentication.getName(),"Accessing user list");
        model.addAttribute("users",userService.getUsers());
        return "users";
    }

    @GetMapping("/update/{id}")
    public String showUpdateUser(Model model, @PathVariable String id , Authentication authentication) {
        Logger.getInstance().log(authentication.getName(),"Accessing user update page");
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("userPackages", UserPackage.values());
        model.addAttribute("userTypes", UserType.values());
        return "user_update";
    }

    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute UserApiModel user, Authentication authentication) {
        Logger.getInstance().log(authentication.getName(),"Updated user: "+user.getUsername());
        userService.updateUser(user);
        return "redirect:/home";
    }
}
