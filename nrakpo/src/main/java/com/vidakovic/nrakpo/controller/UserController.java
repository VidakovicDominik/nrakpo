package com.vidakovic.nrakpo.controller;

import com.vidakovic.nrakpo.controller.apimodel.UserApiModel;
import com.vidakovic.nrakpo.data.entity.User;
import com.vidakovic.nrakpo.data.entity.enums.UserPackage;
import com.vidakovic.nrakpo.data.entity.enums.UserType;
import com.vidakovic.nrakpo.data.repository.UserRepository;
import com.vidakovic.nrakpo.service.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping()
    public String showIndex(Model model) {
        model.addAttribute("users",toApiModel(userRepository.findAll()));
        return "users";
    }

    @GetMapping("/update/{id}")
    public String showUpdatePhoto(Model model, @PathVariable String id) {
        Optional<User> user = userRepository.findById(id);
        model.addAttribute("user", user.get());
        model.addAttribute("userPackages", UserPackage.values());
        model.addAttribute("userTypes", UserType.values());
        return "user_update";
    }

    @PostMapping("/update")
    public String updatePhoto(@Valid @ModelAttribute UserApiModel user, Authentication authentication) {
        userService.updateUser(user);
        return "redirect:/home";
    }


    private List<UserApiModel> toApiModel(Iterable<User> users){
            List<UserApiModel> userApiModels=new ArrayList<>();
            for (User user :
                    users) {
                userApiModels.add(new UserApiModel(user));
            }
            return userApiModels;
        }
}
