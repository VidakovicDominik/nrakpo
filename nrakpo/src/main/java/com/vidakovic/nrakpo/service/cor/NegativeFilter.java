package com.vidakovic.nrakpo.service.cor;

import com.vidakovic.nrakpo.controller.apimodel.FilteredPhoto;

import java.util.List;

public class NegativeFilter extends AbstractFilter {

    @Override
    protected void applyFilter(FilteredPhoto photo, List<String> filters) {
        if(filters.contains(FilterType.NEGATIVE.toString())) {
            photo.addFilter(FilterType.NEGATIVE.toString());
        }
    }
}
