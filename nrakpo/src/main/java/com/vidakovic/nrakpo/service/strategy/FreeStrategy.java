package com.vidakovic.nrakpo.service.strategy;

public class FreeStrategy implements Strategy {
    @Override
    public boolean exeededLimit(Long picturesUploaded) {
        if(picturesUploaded<=10){
            return false;
        }
        return true;
    }
}
