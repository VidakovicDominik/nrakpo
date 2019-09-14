package com.vidakovic.nrakpo.service.cor;

public class BlackAndWhiteFilter extends AbstractFilter {
    @Override
    protected void applyFilter(FilteredPhoto photo) {
        photo.addFilter("black&white");
    }
}
