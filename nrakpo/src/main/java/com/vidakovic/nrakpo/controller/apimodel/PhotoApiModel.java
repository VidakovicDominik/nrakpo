package com.vidakovic.nrakpo.controller.apimodel;

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
    private String description;

    private String url;

    private String size;

    private String format;

    private String hashtags;

    public PhotoApiModel(String description, String url, String size, String format, String hashtags) {
        this.description = description;
        this.url = url;
        this.size = size;
        this.format = format;
        this.hashtags = hashtags;
    }

    public PhotoApiModel() {
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

