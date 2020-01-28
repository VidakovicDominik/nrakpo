package com.vidakovic.nrakpo.service.criteria;

import com.vidakovic.nrakpo.data.entity.Photo;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorCriteria implements Criteria {

    String author;

    public AuthorCriteria(String author) {
        this.author = author;
    }

    @Override
    public List<Photo> criteriaCheck(List<Photo> photos) {
        return photos.stream()
                .filter(p->p.getUser().getUsername().equals(author))
                .collect(Collectors.toList());
    }
}
