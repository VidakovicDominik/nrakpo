package com.vidakovic.nrakpo.controller.form;

public class CriteriaForm {

    private String author;

    private String hashtags;

    private String sizeX;

    private String sizeY;

    private String dateFrom;

    private String dateTo;

    public CriteriaForm(String author, String hashtags, String sizeX, String sizeY, String dateFrom, String dateTo) {
        this.author = author;
        this.hashtags = hashtags;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public CriteriaForm() {
    }

    public String getHashtags() {
        return hashtags;
    }

    public void setHashtags(String hashtags) {
        this.hashtags = hashtags;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
}
