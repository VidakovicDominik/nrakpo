package com.vidakovic.nrakpo.service.criteria;

import com.vidakovic.nrakpo.data.entity.Photo;

import java.util.List;
import java.util.stream.Collectors;

public class SizeCriteria implements Criteria {

    private String size;

    public SizeCriteria(String size) {
        this.size = size;
    }

    @Override
    public List<Photo> criteriaCheck(List<Photo> photos) {
        return photos.stream()
                .filter(x->x.getSize().equals(size))
                .collect(Collectors.toList());
    }
}
