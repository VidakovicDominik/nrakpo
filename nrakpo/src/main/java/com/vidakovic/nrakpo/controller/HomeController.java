package com.vidakovic.nrakpo.controller;

import com.vidakovic.nrakpo.data.entity.Hashtag;
import com.vidakovic.nrakpo.data.entity.enums.ImageFormat;
import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.data.repository.PhotoRepository;
import com.vidakovic.nrakpo.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/home")
    public String showIndex(Model model) {
        List<Photo> photos= (List<Photo>)photoRepository.findAll();
        model.addAttribute("photos",photos);
        return "home";
    }

    @GetMapping("/mock")
    public String mock(Model model){
        Set<Hashtag> hashtags=new HashSet<>();
        hashtags.add(new Hashtag("sad"));
        hashtags.add(new Hashtag("cat"));
        hashtags.add(new Hashtag("cry"));
        hashtags.add(new Hashtag("poor bastard"));
        hashtags.add(new Hashtag("tears"));
        for (int i=0;i<5;i++){
            save(new Photo(
                    "photo 1",
                    "https://i.kym-cdn.com/entries/icons/original/000/026/489/crying.jpg",
                    "smol",
                    ImageFormat.JPEG,
                    hashtags,
                    userRepository.findById("wbadmin").get()
            ));
        }

        return "redirect:/home";
    }

    @Transactional
    private void save(Photo photo){
        photoRepository.save(photo);
    }



}
