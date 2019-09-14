package com.vidakovic.nrakpo.service.criteria;

import com.vidakovic.nrakpo.controller.apimodel.CriteriaForm;
import com.vidakovic.nrakpo.data.entity.Photo;

import java.util.List;

public interface Filter {
    public List<Photo> filterPhotos(List<Photo> photos);
}
