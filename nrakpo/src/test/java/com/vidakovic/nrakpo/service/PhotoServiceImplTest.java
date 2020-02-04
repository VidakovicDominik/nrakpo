package com.vidakovic.nrakpo.service;

import com.vidakovic.nrakpo.controller.apimodel.PhotoApiModel;
import com.vidakovic.nrakpo.controller.form.CriteriaForm;
import com.vidakovic.nrakpo.data.entity.Hashtag;
import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.data.entity.User;
import com.vidakovic.nrakpo.data.entity.enums.ImageFormat;
import com.vidakovic.nrakpo.data.entity.enums.UserPackage;
import com.vidakovic.nrakpo.data.entity.enums.UserType;
import com.vidakovic.nrakpo.data.repository.PhotoRepository;
import com.vidakovic.nrakpo.util.DateUtil;
import com.vidakovic.nrakpo.util.HashtagUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class PhotoServiceImplTest extends ServiceTest {

    @MockBean
    PhotoRepository photoRepository;

    @MockBean
    UserServiceImpl userServiceImpl;

    @Autowired
    PhotoServiceImpl photoServiceImpl;

    @Test
    void getPhoto() {
        Optional<Photo> photo= Optional.of(createPhoto());
        when(photoRepository.findById(any())).thenReturn(photo);
        PhotoApiModel photoApiModel= photoServiceImpl.getPhoto(1);

        assertThat(photoApiModel.getDescription()).isEqualTo(photo.get().getDescription());
    }

    @Test
    void insertPhoto() {
        Optional<User> user=Optional.of(new User("user","pass","email", UserType.USER, UserPackage.GOLD));
        when(userRepository.findById(any())).thenReturn(user);
        doNothing().when(userServiceImpl).checkMonthlyConsumption(any());
        PhotoApiModel photoApiModel= createPhotoApiModel();
        photoServiceImpl.insertPhoto(photoApiModel,"user");

        verify(photoRepository).save(any());
    }

    @Test
    void shouldNotInsertPhotoIfMonthlyLimitIsExeeded() {
        Optional<User> user=Optional.of(new User("user","pass","email", UserType.USER, UserPackage.GOLD));
        when(userRepository.findById(any())).thenReturn(user);
        doThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN)).when(userServiceImpl).checkMonthlyConsumption(any());
        PhotoApiModel photoApiModel= createPhotoApiModel();

        assertThrows(HttpClientErrorException.class,()-> photoServiceImpl.insertPhoto(photoApiModel,"user"));
    }

    @Test
    void getAllPhotos() {
        Page<Photo> photos= new PageImpl(Arrays.asList(createPhoto()));
        when(photoRepository.findAll((Pageable) any())).thenReturn(photos);
        Page<PhotoApiModel> photoApiModel= photoServiceImpl.getAllPhotos(new PageRequest(1,1));

        assertThat(photoApiModel.iterator().next().getDescription()).isEqualTo(photos.iterator().next().getDescription());
    }

    @Test
    void filterPhotos() {
        Photo searchedPhoto=new Photo("description1", "url1", "50X50", ImageFormat.JPEG, Arrays.asList(new Hashtag("hashtag")), new User("user1","pass","email", UserType.USER, UserPackage.GOLD));
        Iterable<Photo> photos=Arrays.asList(
                searchedPhoto,
                new Photo("description2", "url2", "50X60", ImageFormat.BMP, Arrays.asList(new Hashtag("hashtag2")), new User("user2","pass","email", UserType.USER, UserPackage.GOLD)),
                new Photo("description3", "url3", "50X70", ImageFormat.BMP, Arrays.asList(new Hashtag("hashtag2")), new User("user3","pass","email", UserType.USER, UserPackage.GOLD)),
                new Photo("description4", "url4", "50X80", ImageFormat.BMP, Arrays.asList(new Hashtag("hashtag2")), new User("user4","pass","email", UserType.USER, UserPackage.GOLD)),
                new Photo("description5", "url5", "50X90", ImageFormat.BMP, Arrays.asList(new Hashtag("hashtag2")), new User("user5","pass","email", UserType.USER, UserPackage.GOLD))
                );
        when(photoRepository.findAll()).thenReturn(photos);
        CriteriaForm criteriaForm=new CriteriaForm(searchedPhoto.getUser().getUsername(),
                new HashtagUtil().parseHashtagsToString(searchedPhoto.getHashtags()),
                searchedPhoto.getSize().split("X")[0],
                searchedPhoto.getSize().split("X")[1],
                new DateUtil().getSimpleDate(new Date().getTime()-1000000000L),
                new DateUtil().getSimpleDate(new Date().getTime()+1000000000L)
        );

        assertThat(photoServiceImpl.filterPhotos(criteriaForm).iterator().next().getDescription()).isEqualTo(searchedPhoto.getDescription());
    }

    private Photo createPhoto() {
        return new Photo("description", "url", "sizexXsizey", ImageFormat.JPEG, Collections.emptyList(), new User());
    }

    private PhotoApiModel createPhotoApiModel() {
        return new PhotoApiModel(1, "description", "url", "sizex", "sizey", "JPEG", "hashtags", "date", "username");
    }
}
