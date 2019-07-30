package com.vidakovic.nrakpo.data.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class Photo {

    @Id
    private Integer id;

    private String description;

    private String url;

    private String size;

    private String format;

    private Long date;

    private List<String> hashtags;

    @ManyToOne
    private User user;

    public Photo(String description, String url, String size, String format, Long date, List<String> hashtags, User user) {
        this.description = description;
        this.url = url;
        this.size = size;
        this.format = format;
        this.date = date;
        this.hashtags = hashtags;
        this.user = user;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

