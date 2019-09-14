package com.vidakovic.nrakpo.service.cor;

public abstract class AbstractFilter {

    protected AbstractFilter nextFilter;

    public void setNextFilter(AbstractFilter filter){
        this.nextFilter=filter;
    }

    public void filterPhoto(FilteredPhoto photo){
        applyFilter(photo);

        if(nextFilter!=null){
            nextFilter.filterPhoto(photo);
        }
    }

    protected abstract void applyFilter(FilteredPhoto photo);
}

