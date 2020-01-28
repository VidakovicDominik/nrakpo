package com.vidakovic.nrakpo.util;


import com.vidakovic.nrakpo.data.entity.Hashtag;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HashtagUtil {
    public static final String HASHTAG="#";

    public List<Hashtag> parseHashtagsToList(String rawHashtags) {
        return Arrays.asList(rawHashtags.split(HASHTAG)).stream().map(Hashtag::new).collect(Collectors.toList());
    }

    public String parseHashtagsToString(List<Hashtag> hashtags){
        StringBuilder b=new StringBuilder();
        hashtags.forEach(h->b.append(HASHTAG+h.getName()+" "));
        return b.toString();
    }

    public boolean hasCommonHashtag(String searchedHashtags, List<Hashtag> hashtags){
        for (String hashtag :
                Arrays.asList(searchedHashtags.trim().split("#"))) {
            for (Hashtag storedHashtag :
                    hashtags) {
                if (hashtag.equals(storedHashtag.getName()))
                    return true;
            }
        }
        return false;
    }

    public List<String> trimHashtags(List<String> hashtags){
        return hashtags.stream().map(x->x.trim()).collect(Collectors.toList());
    }
}

