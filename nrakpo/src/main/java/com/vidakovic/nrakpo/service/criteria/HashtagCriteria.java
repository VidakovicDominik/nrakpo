package com.vidakovic.nrakpo.service.criteria;

import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.util.HashtagUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HashtagCriteria implements Criteria {

    List<String> hashtags;

    public HashtagCriteria(String hashtags) {
        this.hashtags = Arrays.asList(hashtags.trim().split(HashtagUtil.HASHTAG));
    }

    @Override
    public List<Photo> criteriaCheck(List<Photo> photos) {
        return photos.stream()
                .filter(p -> {
                    hashtags.retainAll(p.getHashtags());
                    return hashtags.size() > 0;
                })
                .collect(Collectors.toList());
    }

}
