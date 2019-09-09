package com.vidakovic.nrakpo.controller;

import com.vidakovic.nrakpo.controller.apimodel.UserApiModel;
import com.vidakovic.nrakpo.data.entity.User;
import com.vidakovic.nrakpo.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping()
    public String showIndex(Model model) {
        model.addAttribute("users",toApiModel(userRepository.findAll()));
        return "users";
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
