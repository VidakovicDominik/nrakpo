package com.vidakovic.nrakpo.service.criteria;

import com.vidakovic.nrakpo.data.entity.Photo;

import java.util.List;

public interface Criteria {
    public List<Photo> criteriaCheck(List<Photo> photos);
}
