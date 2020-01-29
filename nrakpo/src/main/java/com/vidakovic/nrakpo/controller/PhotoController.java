package com.vidakovic.nrakpo.controller;

import com.vidakovic.nrakpo.aspect.Log;
import com.vidakovic.nrakpo.controller.apimodel.PhotoApiModel;
import com.vidakovic.nrakpo.controller.form.CriteriaForm;
import com.vidakovic.nrakpo.data.entity.enums.ImageFormat;
import com.vidakovic.nrakpo.service.PhotoService;
import com.vidakovic.nrakpo.service.cor.FilterType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;

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

    public PhotoController(PhotoService photoService){
        this.photoService=photoService;
    }

    @GetMapping("/{id}")
    public String showPhotoDetails(Model model, @PathVariable Integer id , Authentication authentication) {
        model.addAttribute("photo", photoService.getPhoto(id));
        model.addAttribute("filters", FilterType.values());
        return "photo_details";
    }

    @GetMapping("/update/{id}")
    public String showUpdatePhoto(Model model, @PathVariable Integer id , Authentication authentication, HttpServletRequest request) {
        PhotoApiModel photo = photoService.getPhoto(id);
        if(!photo.getUsername().equals(authentication.getName())||!request.isUserInRole("ADMIN")){
            return "redirect:/home";
        }
        model.addAttribute("photo", photo);
        model.addAttribute("selectedFormat", photo.getFormat());
        model.addAttribute("formats", ImageFormat.values());
        return "photo_update";
    }

    @PostMapping("/filter")
    @Log(message = "Filtering images")
    public String filterPhotos(Model model, @ModelAttribute CriteriaForm criteriaForm, Authentication authentication){
        model.addAttribute("photos", photoService.filterPhotos(criteriaForm));
        return "filtered_view";
    }

    @PostMapping("/update")
    @Log(message = "Updating image information")
    public String updatePhoto(@Valid @ModelAttribute PhotoApiModel photo, Authentication authentication) {
        photoService.updatePhoto(photo);
        return "redirect:/home";
    }

    @PostMapping("/download/{id}")
    @Log(message = "Downloading image")
    public String downloadAndFilterPhoto(Model model, @PathVariable Integer id,  @RequestParam(value = "pickedFilters" , required = false) String[] filters, Authentication authentication){
        model.addAttribute("photo",photoService.downloadPhoto(id, filters==null?new ArrayList():Arrays.asList(filters)));
        return "photo_download";
    }

}
