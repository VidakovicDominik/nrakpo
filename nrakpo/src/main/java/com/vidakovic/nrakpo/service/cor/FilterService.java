package com.vidakovic.nrakpo.service.cor;

import com.vidakovic.nrakpo.controller.apimodel.FilteredPhoto;
import org.springframework.stereotype.Service;

import java.util.List;

//srp
@Service
public class FilterService {

    public FilteredPhoto getFilteredPhoto(FilteredPhoto photo, List<String> filters){
        AbstractFilter sepiaFilter=new SepiaFilter();
        AbstractFilter bnwFilter=new BlackAndWhiteFilter();
        AbstractFilter negativeFilter=new NegativeFilter();

        sepiaFilter.setNextFilter(bnwFilter);
        bnwFilter.setNextFilter(negativeFilter);

        return sepiaFilter.filterPhoto(photo, filters);
    }
}
