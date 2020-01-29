package com.vidakovic.nrakpo.controller;

import com.vidakovic.nrakpo.aspect.Log;
import com.vidakovic.nrakpo.controller.apimodel.PhotoApiModel;
import com.vidakovic.nrakpo.data.entity.enums.ImageFormat;
import com.vidakovic.nrakpo.service.PhotoService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * <p>
 * <b>Title: InputController  </b>
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
 * PA1 20-Aug-19
 * @since 20-Aug-19 13:26:42
 */

@Controller
@RequestMapping("/pictureUpload")
public class InputController {

    PhotoService photoService;

    public InputController(PhotoService photoService){
        this.photoService=photoService;
    }

    @GetMapping
    public String showPictureUpload(Model model, Authentication authentication){
        model.addAttribute("photo",new PhotoApiModel());
        model.addAttribute("formats", ImageFormat.values());
        return "upload";
    }

    @PostMapping
    @Log(message = "Uploading picture")
    public String showPictureUpload(@Valid @ModelAttribute PhotoApiModel photo, Authentication authentication){
        photoService.insertPhoto(photo,authentication.getName());
        return "redirect:/home";
    }
}

