package com.vidakovic.nrakpo.service.builder;

import com.vidakovic.nrakpo.data.entity.Hashtag;
import com.vidakovic.nrakpo.data.entity.LocalUser;
import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.data.entity.User;
import com.vidakovic.nrakpo.data.entity.enums.AccountType;
import com.vidakovic.nrakpo.data.entity.enums.ImageFormat;
import com.vidakovic.nrakpo.data.entity.enums.UserPackage;
import com.vidakovic.nrakpo.data.entity.enums.UserType;

import java.util.Date;

public class PhotoBuilder {
    private static int buffer=0;
    private Photo photo;

    public PhotoBuilder() {
        this.reset();
    }

    public void reset(){
    this.photo=new Photo();
    buffer++;
    }

    public Photo getProduct(){
        return photo;
    }

    public void setDescription(){
        this.photo.setDescription("descrpition"+buffer);
    }

    public void setSize(){
        this.photo.setSize("50X50");
    }

    public void setUser(){
        LocalUser user=new LocalUser();
        user.setUsername("user"+buffer);
        user.setUserPackage(UserPackage.GOLD);
        user.setEmail("email");
        user.setPassword("123");
        user.setUserType(UserType.USER);
        this.photo.setUser(user);
    }

    public void setDate(){
        this.photo.setDate(new Date().getTime());
    }

    public void setHashtags(){
        for (int i = 0; i < 5; i++) {
            Hashtag hashtag = new Hashtag();
            hashtag.setName("tag"+buffer+i);
            hashtag.setPhoto(photo);
            this.photo.addHashtag(hashtag);
        }
    }

     public void setFormat(){
        this.photo.setFormat(ImageFormat.JPEG);
     }

     public void setUrl(){
        this.photo.setUrl("https://i.kym-cdn.com/entries/icons/original/000/026/489/crying.jpg");
     }


}
