package com.vidakovic.nrakpo.service;

import com.vidakovic.nrakpo.data.entity.LocalUser;
import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.data.entity.enums.UserPackage;
import com.vidakovic.nrakpo.data.entity.enums.UserType;
import com.vidakovic.nrakpo.data.repository.HashtagRepository;
import com.vidakovic.nrakpo.data.repository.PhotoRepository;
import com.vidakovic.nrakpo.data.repository.UserRepository;
import com.vidakovic.nrakpo.service.builder.PhotoBuilder;
import com.vidakovic.nrakpo.service.builder.PhotoMockDirector;
import org.springframework.stereotype.Service;

@Service
public class TestSupportServiceImpl implements TestSupportService {
    PhotoRepository photoRepository;
    HashtagRepository hashtagRepository;
    UserRepository userRepository;

    public TestSupportServiceImpl(PhotoRepository photoRepository, HashtagRepository hashtagRepository, UserRepository userRepository) {
        this.photoRepository = photoRepository;
        this.hashtagRepository = hashtagRepository;
        this.userRepository =userRepository;
    }

    public void mock() {
        LocalUser user = getLocalUser();
        userRepository.save(user);
        PhotoBuilder builder = new PhotoBuilder();
        for (int i = 0; i < 9; i++) {
            PhotoMockDirector director = new PhotoMockDirector(builder);
            director.buildPhoto();
            Photo photo = builder.getProduct();
            photo.setUser(user);
            photoRepository.save(photo);
            hashtagRepository.saveAll(photo.getHashtags());
            builder.reset();
        }
    }

    private LocalUser getLocalUser() {
        LocalUser user=new LocalUser();
        user.setUsername("user");
        user.setUserPackage(UserPackage.GOLD);
        user.setEmail("email");
        user.setPassword("123");
        user.setUserType(UserType.USER);
        return user;
    }

}

