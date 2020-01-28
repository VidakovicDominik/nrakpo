package com.vidakovic.nrakpo.data.repository;

import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.data.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PhotoRepository extends PagingAndSortingRepository<Photo, Integer> {
    Long countByUserAndDateBetween(User user, Long date1, Long date2);
}
