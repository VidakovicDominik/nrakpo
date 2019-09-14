package com.vidakovic.nrakpo.service.criteria;

import com.vidakovic.nrakpo.data.entity.Photo;

import java.util.ArrayList;
import java.util.List;

public class SizeCriteria implements Criteria {

    private String size;

    public SizeCriteria(String size) {
        this.size = size;
    }

    @Override
    public List<Photo> criteriaCheck(List<Photo> photos) {
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
