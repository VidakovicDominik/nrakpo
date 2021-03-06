package com.vidakovic.nrakpo.controller.apimodel;

import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.util.DateUtil;
import com.vidakovic.nrakpo.util.HashtagUtil;

public class PhotoApiModel implements LoggableApiModel{
    private Integer id;

    private String description;

    private String url;

    private String sizeX;

    private String sizeY;

    private String format;

    private String hashtags;

    private String date;

    private String username;

    public PhotoApiModel(Integer id, String description, String url, String sizeX, String sizeY, String format, String hashtags, String date, String username) {
        this.id = id;
        this.description = description;
        this.url = url;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.format = format;
        this.hashtags = hashtags;
        this.date = date;
        this.username = username;
    }

    public PhotoApiModel(Photo photo){
        this.id=photo.getId();
        this.description=photo.getDescription();
        this.url=photo.getUrl();
        this.sizeX=photo.getSize().split("X")[0];
        this.sizeY=photo.getSize().split("X")[1];
        this.format=photo.getFormat().toString();
        this.date = new DateUtil().getSimpleDate(photo.getDate());
        this.username=photo.getUser().getUsername();
        this.hashtags=new HashtagUtil().parseHashtagsToString(photo.getHashtags());;
    }

    public PhotoApiModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSizeX() {
        return sizeX;
    }

    public void setSizeX(String sizeX) {
        this.sizeX = sizeX;
    }

    public String getSizeY() {
        return sizeY;
    }

    public void setSizeY(String sizeY) {
        this.sizeY = sizeY;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getHashtags() {
        return hashtags;
    }

    public void setHashtags(String hashtags) {
        this.hashtags = hashtags;
    }

    @Override
    public String logText() {
        return "Received photo info-> Date:"+ this.date+ " Description:"+this.description+ " Image URL:"+this.url;
    }
}

