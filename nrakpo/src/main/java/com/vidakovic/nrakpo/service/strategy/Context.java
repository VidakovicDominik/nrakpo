package com.vidakovic.nrakpo.service.strategy;

public class Context {
    private Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public boolean executeStrategy(Long uploadedPictures){
        return strategy.exeededLimit(uploadedPictures);
    }
}
