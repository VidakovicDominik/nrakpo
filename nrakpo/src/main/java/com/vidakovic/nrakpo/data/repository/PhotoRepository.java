package com.vidakovic.nrakpo.data.repository;

import com.vidakovic.nrakpo.data.entity.Hashtag;
import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.data.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface PhotoRepository extends CrudRepository<Photo, Integer> {
    List<Photo> findAllByHashtagsAndSizeAndDateBetweenAndUser(Set<Hashtag> hashtags, String size, Long date1, Long date2, User user);

    Long countByUserAndDateBetween(User user, Long date1, Long date2);
}
