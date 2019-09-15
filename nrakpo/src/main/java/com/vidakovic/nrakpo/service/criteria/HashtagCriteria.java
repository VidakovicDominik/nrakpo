package com.vidakovic.nrakpo.service.criteria;

import com.vidakovic.nrakpo.data.entity.Hashtag;
import com.vidakovic.nrakpo.data.entity.Photo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HashtagCriteria implements Criteria {

    List<String> hashtags;

    public HashtagCriteria(String hashtags) {
        this.hashtags = Arrays.asList(hashtags.trim().split("#"));
    }


    @Override
    public List<Photo> criteriaCheck(List<Photo> photos) {
        trimHashtags();
        List<Photo> photosWithContainingHashtags=new ArrayList<>();
        for (Photo photo :
                photos) {
            for (Hashtag hashtag:
                 photo.getHashtags()) {
                if(hashtags.contains(hashtag.getName())){
                    photosWithContainingHashtags.add(photo);
                    continue;
                }
            }
        }
        return photosWithContainingHashtags;
    }

    private void trimHashtags(){
        List<String> trimmedHashtags=new ArrayList<>();
        for (String hashtag :
                hashtags) {
            trimmedHashtags.add(hashtag.trim());
        }
        hashtags=trimmedHashtags;
    }
}
