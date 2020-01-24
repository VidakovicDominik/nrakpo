package com.vidakovic.nrakpo.controller;

import com.vidakovic.nrakpo.controller.form.CriteriaForm;
import com.vidakovic.nrakpo.data.repository.HashtagRepository;
import com.vidakovic.nrakpo.data.repository.PhotoRepository;
import com.vidakovic.nrakpo.data.repository.UserRepository;
import com.vidakovic.nrakpo.service.PhotoService;
import com.vidakovic.nrakpo.service.PhotoServiceImpl;
import com.vidakovic.nrakpo.service.singleton.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    HashtagRepository hashtagRepository;

    PhotoService photoService;

    public HomeController(PhotoServiceImpl photoService) {
        this.photoService = photoService;
    }

    @GetMapping
    public String showIndex() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String showHome(@PageableDefault(size = 10) Pageable pageable, Model model, Authentication authentication) {
        photoService.stats();
        if (authentication == null) {
            Logger.getInstance().log("Anonymous user", "Accessing home page");
        } else {
            Logger.getInstance().log(authentication.getName(), "Accessing home page");
        }
        model.addAttribute("page", photoService.getAllPhotos(pageable));
        model.addAttribute("criteriaForm", new CriteriaForm());
        return "home";
    }

    @GetMapping("/mock")
    public String mock(Model model) {
        photoService.mock();
        return "redirect:/home";
    }

}
