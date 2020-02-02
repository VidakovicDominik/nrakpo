package com.vidakovic.nrakpo.data.entity;

import com.vidakovic.nrakpo.data.entity.enums.ImageFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @OneToMany(mappedBy = "photo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Hashtag> hashtags = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.MERGE)
    private User user;

    public Photo(String description, String url, String size, ImageFormat format, List<Hashtag> hashtags, User user) {
        this.description = description;
        this.url = url;
        this.size = size;
        this.format = format;
        this.date = new Date().getTime();
        this.hashtags = hashtags;
        for (Hashtag hashtag :
                hashtags) {
            hashtag.setPhoto(this);
        }
        this.user = user;
    }

    public void addHashtag(Hashtag hashtag){
        this.hashtags.add(hashtag);
        hashtag.setPhoto(this);
    }
    public Photo() {
    }

    public Integer getId() {
        return id;
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

    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<Hashtag> hashtags) {
        this.hashtags = hashtags;
        for (Hashtag hashtag :
                hashtags) {
            hashtag.setPhoto(this);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

