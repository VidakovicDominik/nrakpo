package com.vidakovic.nrakpo.controller;

import com.vidakovic.nrakpo.controller.form.RegistrationForm;
import com.vidakovic.nrakpo.data.entity.enums.AccountType;
import com.vidakovic.nrakpo.data.entity.enums.UserPackage;
import com.vidakovic.nrakpo.service.UserService;
import com.vidakovic.nrakpo.service.singleton.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new RegistrationForm());
        model.addAttribute("userPackages", UserPackage.values());
        model.addAttribute("accountTypes", AccountType.values());
        return "register";
    }

    @PostMapping
    public String processRegistration(@ModelAttribute RegistrationForm form) {
        Logger.getInstance().log(form.getUsername(),"Just registered");
        userService.insertUser(form);
        return "redirect:/login";
    }
}
