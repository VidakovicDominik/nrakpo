package com.vidakovic.nrakpo.service.builder;

public class PhotoMockDirector {
    private PhotoBuilder builder;

    public PhotoMockDirector(PhotoBuilder builder) {
        this.builder = builder;
    }

    public void buildPhoto(){
        builder.setDate();
        builder.setDescription();
        builder.setFormat();
        builder.setHashtags();
        builder.setSize();
        builder.setUser();
        builder.setUrl();
    }
}
