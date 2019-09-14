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

public class AuthorFilter implements Filter {

    String author;

    public AuthorFilter(String author) {
        this.author = author;
    }

    @Override
    public List<Photo> filterPhotos(List<Photo> photos) {
        List<Photo> photosWithAuthor=new ArrayList<>();
        for (Photo photo :
                photos) {
            if(photo.getUser().getUsername().equals(author)){
                photosWithAuthor.add(photo);
            }
        }
        return photosWithAuthor;
    }
}
