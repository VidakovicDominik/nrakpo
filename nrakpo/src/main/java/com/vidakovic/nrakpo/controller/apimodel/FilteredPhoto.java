package com.vidakovic.nrakpo.controller.apimodel;

import com.vidakovic.nrakpo.data.entity.Photo;

public class FilteredPhoto extends PhotoApiModel {
    private String appliedFilters="";

    public FilteredPhoto(){}

    public FilteredPhoto(Photo photo) {
        super(photo);
    }

    public void addFilter(String filter){
        appliedFilters=appliedFilters+", "+filter;
    }

    public String getAppliedFilters() {
        return appliedFilters;
    }

    public void setAppliedFilters(String appliedFilters) {
        this.appliedFilters = appliedFilters;
    }
}
