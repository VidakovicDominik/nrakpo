package com.vidakovic.nrakpo.service.cor;

import com.vidakovic.nrakpo.controller.apimodel.PhotoApiModel;

import java.util.ArrayList;
import java.util.List;

public class FilteredPhoto extends PhotoApiModel {
    private String appliedFilters="";

    public void addFilter(String filter){
        appliedFilters=appliedFilters+", "+filter;
    }
}
