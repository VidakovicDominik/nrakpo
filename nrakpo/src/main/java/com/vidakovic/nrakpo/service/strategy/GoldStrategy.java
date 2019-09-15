package com.vidakovic.nrakpo.service.strategy;

public class GoldStrategy implements Strategy {
    @Override
    public boolean exeededLimit(Long picturesUploaded) {
        return false;
    }
}
