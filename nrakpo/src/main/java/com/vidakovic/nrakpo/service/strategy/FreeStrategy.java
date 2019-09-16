package com.vidakovic.nrakpo.service.strategy;

public class FreeStrategy implements Strategy {
    @Override
    public boolean exeededLimit(Long picturesUploaded) {
        if(picturesUploaded<=5){
            return false;
        }
        return true;
    }
}
