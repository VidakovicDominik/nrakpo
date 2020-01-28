package com.vidakovic.nrakpo.service.criteria;

import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.util.DateUtil;

import java.util.ArrayList;
import java.util.List;


public class DateCriteria implements Criteria {

    private Long dateFrom;
    private Long dateTo;

    public DateCriteria(String dateFrom, String dateTo) {
        DateUtil dateUtil = new DateUtil();
        this.dateFrom = dateUtil.getLongDate(dateFrom);
        this.dateTo = dateUtil.getLongDate(dateTo);
    }

    @Override
    public List<Photo> criteriaCheck(List<Photo> photos) {
        List<Photo> photosMatchingDate=new ArrayList<>();
        for (Photo photo:
             photos) {
            if(photo.getDate()>dateFrom&&photo.getDate()<dateTo){
                photosMatchingDate.add(photo);
            }
        }
        return photosMatchingDate;
    }
}
