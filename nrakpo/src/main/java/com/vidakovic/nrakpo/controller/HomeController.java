package com.vidakovic.nrakpo.controller;

import com.vidakovic.nrakpo.data.entity.Hashtag;
import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.data.entity.enums.ImageFormat;
import com.vidakovic.nrakpo.data.repository.PhotoRepository;
import com.vidakovic.nrakpo.data.repository.UserRepository;
import com.vidakovic.nrakpo.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    PhotoService photoService;

    public HomeController(PhotoService photoService){
        this.photoService=photoService;
    }

    @GetMapping("/home")
    public String showIndex(Model model) {
        model.addAttribute("photos",photoService.getAllPhotos());
        return "home";
    }

    @GetMapping("/mock")
    public String mock(Model model){
        List<Hashtag> hashtags=new ArrayList<>();
        hashtags.add(new Hashtag("sad"));
        hashtags.add(new Hashtag("cat"));
        hashtags.add(new Hashtag("cry"));
        hashtags.add(new Hashtag("poor bastard"));
        hashtags.add(new Hashtag("tears"));
        for (int i=0;i<5;i++){
            photoRepository.save(new Photo(
                    "photo 1",
                    "https://i.kym-cdn.com/entries/icons/original/000/026/489/crying.jpg",
                    "smol",
                    ImageFormat.JPEG,
                    hashtags,
                    userRepository.findById("admin").get()
            ));
        }

        return "redirect:/home";
    }

}
