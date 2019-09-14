package com.vidakovic.nrakpo.service.cor;

public class NegativeFilter extends AbstractFilter {
    @Override
    protected void applyFilter(FilteredPhoto photo) {
        photo.addFilter("negative");
    }
}
