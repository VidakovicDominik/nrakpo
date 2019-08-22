package com.vidakovic.nrakpo.controller.apimodel;

import com.vidakovic.nrakpo.data.entity.Hashtag;
import com.vidakovic.nrakpo.data.entity.Photo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * <b>Title: Photo  </b>
 * </p>
 * <p>
 * <b> Description:
 *
 *
 * </b>
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) ETK 2019
 * </p>
 * <p>
 * <b>Company:</b> Ericsson Nikola Tesla d.d.
 * </p>
 *
 * @author ezviddo
 * @version PA1
 * <p>
 * <b>Version History:</b>
 * </p>
 * <br>
 * PA1 21-Aug-19
 * @since 21-Aug-19 10:48:42
 */
public class PhotoApiModel {
    private Integer id;

    private String description;

    private String url;

    private String size;

    private String format;

    private String hashtags;

    private String date;

    private String username;

    public PhotoApiModel(Integer id, String description, String url, String size, String format, String hashtags, String date, String username) {
        this.id = id;
        this.description = description;
        this.url = url;
        this.size = size;
        this.format = format;
        this.hashtags = hashtags;
        this.date = date;
        this.username = username;
    }

    public PhotoApiModel(Photo photo){
        this.id=photo.getId();
        this.description=photo.getDescription();
        this.url=photo.getUrl();
        this.size=photo.getSize();
        this.format=photo.getFormat().toString();
        Date date=new Date(photo.getDate());
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
        this.date = df2.format(date);
        this.username=photo.getUser().getUsername();
        String hashtags="";
        for (Hashtag ht :
                photo.getHashtags()) {
            hashtags = hashtags + "#" + ht.getName() + " ";
        }
        this.hashtags=hashtags;
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

    public String getHashtags() {
        return hashtags;
    }

    public void setHashtags(String hashtags) {
        this.hashtags = hashtags;
    }
}

