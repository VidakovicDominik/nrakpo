package com.vidakovic.nrakpo.integration;

import com.vidakovic.nrakpo.controller.apimodel.FilteredPhoto;
import com.vidakovic.nrakpo.controller.apimodel.PhotoApiModel;
import com.vidakovic.nrakpo.controller.form.RegistrationForm;
import com.vidakovic.nrakpo.data.entity.Hashtag;
import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.data.entity.enums.AccountType;
import com.vidakovic.nrakpo.data.entity.enums.ImageFormat;
import com.vidakovic.nrakpo.data.entity.enums.UserPackage;
import com.vidakovic.nrakpo.data.entity.enums.UserType;
import com.vidakovic.nrakpo.data.repository.PhotoRepository;
import com.vidakovic.nrakpo.data.repository.UserRepository;
import com.vidakovic.nrakpo.service.PhotoService;
import com.vidakovic.nrakpo.service.UserService;
import com.vidakovic.nrakpo.service.cor.FilterType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PhotoIntegrationTests {

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    PhotoService photoService;

    @BeforeAll
    @Transactional
    public void createUser() {
        userRepository.deleteAll();
        userService.insertUser(new RegistrationForm("testuser", "pass", "email", UserType.ADMINISTRATOR, UserPackage.GOLD, AccountType.LOCAL));
    }

    @BeforeEach
    @Transactional
    public void cleanDb(){
        photoRepository.deleteAll();
    }

    @Test
    public void insertPhotoTest(){
        String description = "description";
        PhotoApiModel photoApiModel =new PhotoApiModel(null, description, "url", "50", "50", "JPEG", "#hashtag", "2020/12/12","testuser");

        photoService.insertPhoto(photoApiModel,"testuser");

        assertThat(photoRepository.findAll().iterator().next().getDescription()).isEqualTo(description);

    }

    @Test
    public void updatePhotoTest(){
        Photo photo = createPhoto();
        photoRepository.save(photo).getId();

        PhotoApiModel photoApiModel=new PhotoApiModel(photo);
        String newDescription = "new description";
        photoApiModel.setDescription(newDescription);
        String newHashtag = "newhashtag";
        photoApiModel.setHashtags(newHashtag);

        photoService.updatePhoto(photoApiModel);

        assertThat(photoRepository.findById(photo.getId()).get().getDescription()).isEqualTo(newDescription);
        assertThat(photoRepository.findById(photo.getId()).get().getHashtags().iterator().next().getName()).isEqualTo(newHashtag);
    }

    @Test
    public void downloadPhotoTest(){
        Photo photo=photoRepository.save(createPhoto());

        List<String> filters=Arrays.asList(FilterType.SEPIA.toString(),FilterType.NEGATIVE.toString());

        FilteredPhoto filteredPhoto=photoService.downloadPhoto(photo.getId(),filters);

        assertThat(filteredPhoto.getDescription()).isEqualTo(photo.getDescription());
        assertThat(filteredPhoto.getAppliedFilters()).containsSequence(filters.get(0));
        assertThat(filteredPhoto.getAppliedFilters()).containsSequence(filters.get(1));

    }

    private Photo createPhoto() {
        return new Photo("description123", "url", "50X50", ImageFormat.JPEG, Arrays.asList(new Hashtag("hashtag")), userRepository.findById("testuser").get());
    }


}

