package com.vidakovic.nrakpo.controller;

import com.vidakovic.nrakpo.aspect.MeasureTime;
import com.vidakovic.nrakpo.controller.apimodel.PhotoApiModel;
import com.vidakovic.nrakpo.controller.form.CriteriaForm;
import com.vidakovic.nrakpo.data.entity.enums.ImageFormat;
import com.vidakovic.nrakpo.service.PhotoService;
import com.vidakovic.nrakpo.service.cor.FilterType;
import com.vidakovic.nrakpo.service.decorator.PhotoServiceDecorator;
import com.vidakovic.nrakpo.service.singleton.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping("/photo")
public class PhotoController {

    PhotoService photoService;

    public PhotoController(PhotoServiceDecorator photoService){
        this.photoService=photoService;
    }

    @GetMapping("/{id}")
    @MeasureTime(metricName = "photo_details_timer")
    public String showPhotoDetails(Model model, @PathVariable Integer id , Authentication authentication) {
        Logger.getInstance().log(authentication.getName(),"Accessing photo details of photo with id: "+id);
        model.addAttribute("photo", photoService.getPhoto(id));
        model.addAttribute("filters", FilterType.values());
        return "photo_details";
    }

    @GetMapping("/update/{id}")
    public String showUpdatePhoto(Model model, @PathVariable Integer id , Authentication authentication, HttpServletRequest request) {
        Logger.getInstance().log(authentication.getName(),"Accessing photo update page of photo with id: "+id);
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
    @MeasureTime(metricName = "filter_photos_timer")
    public String applyCriteriaToPhotos(Model model, @ModelAttribute CriteriaForm criteriaForm, Authentication authentication){
        Logger.getInstance().log(authentication.getName(),
                "Accessing filter page with the following criteria:" +
                        " author->"+criteriaForm.getAuthor()+
                        " hashtags->"+criteriaForm.getHashtags()+
                        " size->"+ criteriaForm.getSizeX()+"X"+criteriaForm.getSizeY()+
                        "date between"+ criteriaForm.getDateFrom()+ "and"+ criteriaForm.getDateTo()
                );
        model.addAttribute("photos", photoService.filterPhotos(criteriaForm));
        return "filtered_view";
    }

    @PostMapping("/update")
    @MeasureTime(metricName = "update_photo_timer")
    public String updatePhoto(@Valid @ModelAttribute PhotoApiModel photo, Authentication authentication) {
        Logger.getInstance().log(authentication.getName(),"Updated photo with id: "+ photo.getId());
        photoService.updatePhoto(photo,authentication.getName());
        return "redirect:/home";
    }

    @PostMapping("/download/{id}")
    @MeasureTime(metricName = "download_timer")
    public String downloadAndFilterPhoto(Model model, @PathVariable Integer id,  @RequestParam(value = "pickedFilters" , required = false) String[] filters, Authentication authentication){
        Logger.getInstance().log(authentication.getName(),"Downloaded photo with id: "+id);
        model.addAttribute("photo",photoService.downloadPhoto(id, filters==null?new ArrayList():Arrays.asList(filters)));
        return "photo_download";
    }

}
