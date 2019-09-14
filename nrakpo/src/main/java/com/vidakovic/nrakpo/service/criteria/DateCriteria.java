package com.vidakovic.nrakpo.service.criteria;

import com.vidakovic.nrakpo.data.entity.Photo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DateCriteria implements Criteria {

    private Long dateFrom;
    private Long dateTo;

    public DateCriteria(String dateFrom, String dateTo) {
        this.dateFrom = parseDate(dateFrom);
        this.dateTo = parseDate(dateTo);
    }

    private Long parseDate(String date){
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date d = f.parse(date);
            return d.getTime();
        } catch (ParseException e) {

        }
        return 0L;
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
