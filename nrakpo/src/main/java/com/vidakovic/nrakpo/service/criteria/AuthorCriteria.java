package com.vidakovic.nrakpo.service.criteria;

import com.vidakovic.nrakpo.data.entity.Photo;

import java.util.ArrayList;
import java.util.List;

public class AuthorCriteria implements Criteria {

    String author;

    public AuthorCriteria(String author) {
        this.author = author;
    }

    @Override
    public List<Photo> criteriaCheck(List<Photo> photos) {
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
