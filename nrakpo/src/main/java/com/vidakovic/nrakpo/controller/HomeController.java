package com.vidakovic.nrakpo.controller;

import com.vidakovic.nrakpo.controller.apimodel.CriteriaForm;
import com.vidakovic.nrakpo.data.entity.Hashtag;
import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.data.entity.enums.ImageFormat;
import com.vidakovic.nrakpo.data.repository.HashtagRepository;
import com.vidakovic.nrakpo.data.repository.PhotoRepository;
import com.vidakovic.nrakpo.data.repository.UserRepository;
import com.vidakovic.nrakpo.service.PhotoService;
import com.vidakovic.nrakpo.service.singleton.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

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

    public HomeController(PhotoService photoService){
        this.photoService=photoService;
    }

    @GetMapping
    public String showIndex() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String showHome(@PageableDefault(size = 10) Pageable pageable, Model model, Authentication authentication) {
        Logger.getInstance().log(authentication.getName(),"Accessing home page");
        model.addAttribute("page",photoService.getAllPhotos(pageable));
        model.addAttribute("criteriaForm", new CriteriaForm());
        return "home";
    }

    @Transactional
    @GetMapping("/mock")
    public String mock(Model model){
        for(int j=21;j>=0;j--) {
            List<Hashtag> hashtags=new ArrayList<>();
            Photo photo = new Photo(
                    "photo 1",
                    "https://i.kym-cdn.com/entries/icons/original/000/026/489/crying.jpg",
                    "50X50",
                    ImageFormat.JPEG,
                    hashtags,
                    userRepository.findById("admin").get()
            );
            for (int i = 0; i < 5; i++) {
                Hashtag hashtag = new Hashtag();
                hashtag.setName(""+j + i);
                hashtag.setPhoto(photo);
                hashtags.add(hashtag);
                hashtagRepository.save(hashtag);
            }
            photoRepository.save(photo);
        }
        return "redirect:/home";
    }

}
