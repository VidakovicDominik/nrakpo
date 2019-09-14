package com.vidakovic.nrakpo.service.cor;

public class SepiaFilter extends AbstractFilter {
    @Override
    protected void applyFilter(FilteredPhoto photo) {
        photo.addFilter("sepia");
    }
}
