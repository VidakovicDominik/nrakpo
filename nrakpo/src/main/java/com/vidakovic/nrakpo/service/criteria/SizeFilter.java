package com.vidakovic.nrakpo.service.criteria;

import com.vidakovic.nrakpo.controller.apimodel.CriteriaForm;
import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.data.entity.User;
import com.vidakovic.nrakpo.data.repository.PhotoRepository;
import com.vidakovic.nrakpo.data.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SizeFilter implements Filter {

    private String size;

    public SizeFilter(String size) {
        this.size = size;
    }

    @Override
    public List<Photo> filterPhotos(List<Photo> photos) {
        List<Photo> photosMatchingSize=new ArrayList<>();
        for (Photo photo :
                photos) {
            if (photo.getSize().equals(size)){
                photosMatchingSize.add(photo);
            }
        }
        return photosMatchingSize;
    }
}
