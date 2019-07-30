package com.vidakovic.nrakpo.data.repository;

import com.vidakovic.nrakpo.data.entity.Photo;
import org.springframework.data.repository.CrudRepository;

public interface PhotoRepository extends CrudRepository<Photo, Integer> {
}
