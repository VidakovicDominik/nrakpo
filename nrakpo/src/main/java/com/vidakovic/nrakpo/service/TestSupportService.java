package com.vidakovic.nrakpo.service;

import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.data.repository.HashtagRepository;
import com.vidakovic.nrakpo.data.repository.PhotoRepository;
import com.vidakovic.nrakpo.service.builder.PhotoBuilder;
import com.vidakovic.nrakpo.service.builder.PhotoMockDirector;
import org.springframework.stereotype.Service;

@Service
public class TestSupportService {
    PhotoRepository photoRepository;
    HashtagRepository hashtagRepository;

    public TestSupportService(PhotoRepository photoRepository, HashtagRepository hashtagRepository) {
        this.photoRepository = photoRepository;
        this.hashtagRepository = hashtagRepository;
    }

    public void mock() {
        PhotoBuilder builder = new PhotoBuilder();
        for (int i = 0; i < 9; i++) {
            PhotoMockDirector director = new PhotoMockDirector(builder);
            director.buildPhoto();
            Photo photo = builder.getProduct();
            photoRepository.save(photo);
            hashtagRepository.saveAll(photo.getHashtags());
            builder.reset();
        }
    }
}

