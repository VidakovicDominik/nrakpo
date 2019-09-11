package com.vidakovic.nrakpo.controller;

import com.vidakovic.nrakpo.controller.apimodel.PhotoApiModel;
import com.vidakovic.nrakpo.data.entity.enums.ImageFormat;
import com.vidakovic.nrakpo.service.PhotoService;
import com.vidakovic.nrakpo.service.PhotoServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * <b>Title: PhotoController  </b>
 * </p>
 * <p>
 * <b> Description:
 *
 *
 * </b>
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) ETK 2019
 * </p>
 * <p>
 * <b>Company:</b> Ericsson Nikola Tesla d.d.
 * </p>
 *
 * @author ezviddo
 * @version PA1
 * <p>
 * <b>Version History:</b>
 * </p>
 * <br>
 * PA1 04-Sep-19
 * @since 04-Sep-19 16:10:42
 */

@Controller
@RequestMapping("/photo")
public class PhotoController {

    PhotoService photoService;

    public PhotoController(PhotoServiceImpl photoService){
        this.photoService=photoService;
    }

    @GetMapping("/{id}")
    public String showIndex(Model model, @PathVariable Integer id) {
        model.addAttribute("photo", photoService.getPhoto(id));
        return "photo_details";
    }

    @GetMapping("/update/{id}")
    public String showUpdatePhoto(Model model, @PathVariable Integer id) {
        PhotoApiModel photo = photoService.getPhoto(id);
        model.addAttribute("photo", photo);
        model.addAttribute("selectedFormat", photo.getFormat());
        model.addAttribute("formats", ImageFormat.values());
        return "photo_update";
    }

    @PostMapping("/update")
    public String updatePhoto(@Valid @ModelAttribute PhotoApiModel photo, Authentication authentication) {
        photoService.updatePhoto(photo,authentication.getName());
        return "redirect:/home";
    }


}

