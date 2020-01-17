package com.vidakovic.nrakpo.controller;

import com.vidakovic.nrakpo.aspect.MeasureTime;
import com.vidakovic.nrakpo.controller.apimodel.PhotoApiModel;
import com.vidakovic.nrakpo.data.entity.enums.ImageFormat;
import com.vidakovic.nrakpo.service.PhotoService;
import com.vidakovic.nrakpo.service.decorator.PhotoServiceDecorator;
import com.vidakovic.nrakpo.service.singleton.Logger;
import io.micrometer.core.annotation.Timed;
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
@Timed
public class InputController {

    PhotoService photoService;

    public InputController(PhotoServiceDecorator photoService){
        this.photoService=photoService;
    }

    @GetMapping
    public String pictureUpload(Model model, Authentication authentication){
        Logger.getInstance().log(authentication.getName(),"Accessing photo upload page");
        model.addAttribute("photo",new PhotoApiModel());
        model.addAttribute("formats", ImageFormat.values());
        return "upload";
    }

    @PostMapping
    @MeasureTime(metricName = "picture_upload_timer")
    public String pictureUpload(@Valid @ModelAttribute PhotoApiModel photo, Authentication authentication){
        Logger.getInstance().log(authentication.getName(),"Uploading photo with description: "+photo.getDescription());
        photoService.insertPhoto(photo,authentication.getName());
        return "redirect:/home";
    }
}

