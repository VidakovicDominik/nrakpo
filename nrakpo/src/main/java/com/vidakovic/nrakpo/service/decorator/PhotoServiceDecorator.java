package com.vidakovic.nrakpo.service.decorator;

import com.vidakovic.nrakpo.controller.apimodel.FilteredPhoto;
import com.vidakovic.nrakpo.controller.apimodel.PhotoApiModel;
import com.vidakovic.nrakpo.controller.form.CriteriaForm;
import com.vidakovic.nrakpo.data.entity.User;
import com.vidakovic.nrakpo.data.entity.enums.UserType;
import com.vidakovic.nrakpo.service.PhotoService;
import com.vidakovic.nrakpo.service.PhotoServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PhotoServiceDecorator implements PhotoService {

    private PhotoServiceImpl photoService;

    public PhotoServiceDecorator(PhotoServiceImpl photoService) {
        this.photoService = photoService;
    }


    @Override
    public PhotoApiModel getPhoto(Integer id) {
        return photoService.getPhoto(id);
    }

    @Override
    public void insertPhoto(PhotoApiModel photo, String username) {
        photoService.insertPhoto(photo,username);
    }

    @Override
    public Page<PhotoApiModel> getAllPhotos(Pageable pageable) {
        return photoService.getAllPhotos(pageable);
    }

    @Override
    public void updatePhoto(PhotoApiModel photo, String username) {
        photoService.updatePhoto(photo,username);
    }

    @Override
    public boolean checkMonthlyConsumption(User user) {
        if(user.getUserType().equals(UserType.ADMINISTRATOR)){
            return false;
        }
        return photoService.checkMonthlyConsumption(user);
    }


    @Override
    public List<PhotoApiModel> filterPhotos(CriteriaForm criteriaForm) {
        return photoService.filterPhotos(criteriaForm);
    }

    @Override
    public FilteredPhoto downloadPhoto(Integer id, List<String> filters) {
        return photoService.downloadPhoto(id,filters);
    }

    @Override
    public Map<String, Long> stats() {
        return null;
    }

    @Override
    public void mock() {
        photoService.mock();
    }
}

