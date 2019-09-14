package com.vidakovic.nrakpo.service.criteria;

import com.vidakovic.nrakpo.controller.apimodel.CriteriaForm;
import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.data.entity.User;
import com.vidakovic.nrakpo.data.repository.PhotoRepository;
import com.vidakovic.nrakpo.data.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public class DateFilter implements Filter {

    private Long dateFrom;
    private Long dateTo;

    public DateFilter(String dateFrom, String dateTo) {
        this.dateFrom = parseDate(dateFrom);
        this.dateTo = parseDate(dateTo);
    }

    private Long parseDate(String date){
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date d = f.parse(date);
            return d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    @Override
    public List<Photo> filterPhotos(List<Photo> photos) {
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
