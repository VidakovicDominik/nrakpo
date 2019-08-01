package com.vidakovic.nrakpo.data.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Hashtag {
    @Id
    private String name;

    @ManyToMany
    private List<Photo> photos;

    public Hashtag(String name) {
        this.name = name;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

