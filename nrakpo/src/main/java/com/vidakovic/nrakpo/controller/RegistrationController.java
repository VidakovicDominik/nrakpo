package com.vidakovic.nrakpo.controller;

import com.vidakovic.nrakpo.aspect.Log;
import com.vidakovic.nrakpo.aspect.MeasureTime;
import com.vidakovic.nrakpo.controller.form.RegistrationForm;
import com.vidakovic.nrakpo.data.entity.enums.AccountType;
import com.vidakovic.nrakpo.data.entity.enums.UserPackage;
import com.vidakovic.nrakpo.service.UserService;
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
    @MeasureTime(metricName = "registration_timer")
    @Log(message = "Registering user")
    public String processRegistration(@ModelAttribute RegistrationForm form) {
        userService.insertUser(form);
        return "redirect:/login";
    }
}
