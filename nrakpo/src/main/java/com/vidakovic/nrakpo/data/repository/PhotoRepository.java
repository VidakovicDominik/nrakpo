package com.vidakovic.nrakpo.data.repository;

import com.vidakovic.nrakpo.data.entity.Hashtag;
import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.data.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Set;

public interface PhotoRepository extends PagingAndSortingRepository<Photo, Integer> {
    List<Photo> findAllByHashtagsAndSizeAndDateBetweenAndUser(Set<Hashtag> hashtags, String size, Long date1, Long date2, User user);
    List<Photo> findByUser(User user);
    Long countByUserAndDateBetween(User user, Long date1, Long date2);
    List<Photo> findByDateBetween(Long date1, Long date2);
    List<Photo> findBySize(String size);
}
