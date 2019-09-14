package com.vidakovic.nrakpo.service.criteria;

import com.vidakovic.nrakpo.data.entity.Photo;

import java.util.List;

public class OrCriteria implements Filter {

    private Filter Filter;
    private Filter otherFilter;

    public OrCriteria(Filter Filter, Filter otherFilter) {
        this.Filter = Filter;
        this.otherFilter = otherFilter;
    }

    @Override
    public List<Photo> filterPhotos(List<Photo> Photos) {
        List<Photo> firstFilterItems = Filter.filterPhotos(Photos);
        List<Photo> otherFilterItems = otherFilter.filterPhotos(Photos);

        for (Photo Photo : otherFilterItems) {
            if(!firstFilterItems.contains(Photo)){
                firstFilterItems.add(Photo);
            }
        }
        return firstFilterItems;
    }
}
