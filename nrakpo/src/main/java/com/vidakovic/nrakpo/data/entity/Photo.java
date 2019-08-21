package com.vidakovic.nrakpo.data.entity;

import com.vidakovic.nrakpo.controller.apimodel.PhotoApiModel;
import com.vidakovic.nrakpo.data.entity.enums.ImageFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String description;

    private String url;

    private String size;

    private ImageFormat format;

    private Long date;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Set<Hashtag> hashtags;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    public Photo(String description, String url, String size, ImageFormat format, Set<Hashtag> hashtags, User user) {
        this.description = description;
        this.url = url;
        this.size = size;
        this.format = format;
        this.date = new Date().getTime();
        this.hashtags = hashtags;
        this.user = user;
    }

    public Photo(PhotoApiModel photoModel, User user){
        this.description=photoModel.getDescription();
        this.format=ImageFormat.valueOf(photoModel.getFormat());
        this.size=photoModel.getSize();
        this.url=photoModel.getUrl();
        this.user=user;
        this.date= new Date().getTime();
        this.hashtags= parseHashtags(photoModel.getHashtags());
    }

    private Set<Hashtag> parseHashtags(String rawHashtags){
        Set<Hashtag> hashtags=new HashSet<>();
        for (String ht:rawHashtags.split("#")) {
            hashtags.add(new Hashtag(ht.trim()));
        }
        return hashtags;
    }

    public Photo() {
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

    public ImageFormat getFormat() {
        return format;
    }

    public void setFormat(ImageFormat format) {
        this.format = format;
    }

    public Set<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(Set<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

