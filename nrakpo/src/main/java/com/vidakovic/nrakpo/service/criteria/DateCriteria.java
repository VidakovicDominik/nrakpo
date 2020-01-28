package com.vidakovic.nrakpo.service.criteria;

import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.util.DateUtil;

import java.util.List;
import java.util.stream.Collectors;


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
        return photos.stream()
                .filter(p-> p.getDate()>dateFrom&&p.getDate()<dateTo)
                .collect(Collectors.toList());
    }
}
