package com.vidakovic.nrakpo.service.cor;

import com.vidakovic.nrakpo.controller.apimodel.FilteredPhoto;

import java.util.List;

public abstract class AbstractFilter {

    protected AbstractFilter nextFilter;

    public void setNextFilter(AbstractFilter filter){
        this.nextFilter=filter;
    }

    public FilteredPhoto filterPhoto(FilteredPhoto photo, List<String> filters){
        applyFilter(photo, filters);

        if(nextFilter!=null){
            nextFilter.filterPhoto(photo,filters);
        }
        return photo;
    }

    protected abstract void applyFilter(FilteredPhoto photo,List<String> filters);
}

