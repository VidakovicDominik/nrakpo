package com.vidakovic.nrakpo.service.strategy;

public class ProStrategy implements Strategy {
    @Override
    public boolean exeededLimit(Long picturesUploaded) {
        if(picturesUploaded<50){
            return false;
        }
        return true;
    }
}
