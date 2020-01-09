package com.vidakovic.nrakpo.service.criteria;

import com.vidakovic.nrakpo.data.entity.Photo;

import java.util.List;

public class OrCriteria implements Criteria {

    private Criteria criteria;
    private Criteria otherCriteria;

    public OrCriteria(Criteria Criteria, Criteria otherCriteria) {
        this.criteria = Criteria;
        this.otherCriteria = otherCriteria;
    }

    @Override
    public List<Photo> criteriaCheck(List<Photo> Photos) {
        List<Photo> firstFilterItems = criteria.criteriaCheck(Photos);
        List<Photo> otherFilterItems = otherCriteria.criteriaCheck(Photos);

        for (Photo Photo : otherFilterItems) {
            if(!firstFilterItems.contains(Photo)){
                firstFilterItems.add(Photo);
            }
        }
        return firstFilterItems;
    }
}
