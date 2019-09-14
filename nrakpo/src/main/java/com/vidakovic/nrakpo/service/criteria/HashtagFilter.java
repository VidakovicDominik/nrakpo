package com.vidakovic.nrakpo.service.criteria;

import com.vidakovic.nrakpo.controller.apimodel.CriteriaForm;
import com.vidakovic.nrakpo.data.entity.Hashtag;
import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.data.entity.User;
import com.vidakovic.nrakpo.data.repository.HashtagRepository;
import com.vidakovic.nrakpo.data.repository.PhotoRepository;
import com.vidakovic.nrakpo.data.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class HashtagFilter implements Filter {

    List<String> hashtags;

    public HashtagFilter(String hashtags) {
        this.hashtags = Arrays.asList(hashtags.trim().split("#"));
    }


    @Override
    public List<Photo> filterPhotos(List<Photo> photos) {
        List<Photo> photosWithContainingHashtags=new ArrayList<>();
        for (Photo photo :
                photos) {
            for (Hashtag hashtag:
                 photo.getHashtags()) {
                if(hashtags.contains(hashtag.getName())){
                    photosWithContainingHashtags.add(photo);
                    break;
                }
            }
        }
        return photosWithContainingHashtags;
    }
}
