package com.vidakovic.nrakpo.controller;

import com.vidakovic.nrakpo.controller.form.RegistrationForm;
import com.vidakovic.nrakpo.data.entity.User;
import com.vidakovic.nrakpo.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm() {
        return "register";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form) {
        User user=form.toUser(passwordEncoder);
        userRepository.saveUserWithAuthorities(user);
        return "redirect:/login";
    }
}
