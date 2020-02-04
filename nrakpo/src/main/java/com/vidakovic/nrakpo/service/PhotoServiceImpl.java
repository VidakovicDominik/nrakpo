package com.vidakovic.nrakpo.service;

import com.vidakovic.nrakpo.controller.apimodel.FilteredPhoto;
import com.vidakovic.nrakpo.controller.apimodel.PhotoApiModel;
import com.vidakovic.nrakpo.controller.form.CriteriaForm;
import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.data.entity.User;
import com.vidakovic.nrakpo.data.entity.enums.ImageFormat;
import com.vidakovic.nrakpo.data.repository.HashtagRepository;
import com.vidakovic.nrakpo.data.repository.PhotoRepository;
import com.vidakovic.nrakpo.data.repository.UserRepository;
import com.vidakovic.nrakpo.service.cor.FilterService;
import com.vidakovic.nrakpo.service.criteria.CriteriaService;
import com.vidakovic.nrakpo.util.DateUtil;
import com.vidakovic.nrakpo.util.HashtagUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {

    PhotoRepository photoRepository;
    UserRepository userRepository;
    HashtagRepository hashtagRepository;
    FilterService filterService;
    UserServiceImpl userServiceImpl;
    HashtagUtil hashtagUtil;
    DateUtil dateUtil;
    CriteriaService criteriaService;

    public PhotoServiceImpl(PhotoRepository photoRepository,
                            UserRepository userRepository,
                            HashtagRepository hashtagRepository,
                            FilterService filterService,
                            HashtagUtil hashtagUtil,
                            DateUtil dateUtil,
                            UserServiceImpl userServiceImpl,
                            CriteriaService criteriaService) {
        this.photoRepository = photoRepository;
        this.userRepository = userRepository;
        this.hashtagRepository = hashtagRepository;
        this.filterService = filterService;
        this.hashtagUtil = hashtagUtil;
        this.dateUtil = dateUtil;
        this.userServiceImpl = userServiceImpl;
        this.criteriaService = criteriaService;
    }

    public PhotoApiModel getPhoto(Integer id) {
        return new PhotoApiModel(photoRepository.findById(id).get());
    }

    public void insertPhoto(PhotoApiModel photo, String username) {
        User user = userRepository.findById(username).get();
        userServiceImpl.checkMonthlyConsumption(username);
        photoRepository.save(
                new Photo(
                        photo.getDescription(),
                        photo.getUrl(),
                        photo.getSizeX() + "X" + photo.getSizeY(),
                        ImageFormat.valueOf(photo.getFormat()),
                        hashtagUtil.parseHashtagsToList(photo.getHashtags()),
                        user));
    }

    public Page<PhotoApiModel> getAllPhotos(Pageable pageable) {
        return photoRepository.findAll(pageable).map(PhotoApiModel::new);
    }

    @Transactional
    public void updatePhoto(PhotoApiModel photo) {
        Photo oldPhoto = photoRepository.findById(photo.getId()).get();
        hashtagRepository.deleteAll(oldPhoto.getHashtags());
        oldPhoto.setDescription(photo.getDescription());
        oldPhoto.setFormat(ImageFormat.valueOf(photo.getFormat()));
        oldPhoto.setUrl(photo.getUrl());
        oldPhoto.setSize(photo.getSizeX() + "X" + photo.getSizeY());
        oldPhoto.setHashtags(hashtagUtil.parseHashtagsToList(photo.getHashtags()));
        photoRepository.save(oldPhoto);
    }

    public List<PhotoApiModel> filterPhotos(CriteriaForm criteriaForm) {
        return criteriaService.getPhotosByCriteriaUsingStream(criteriaForm);
    }

    public FilteredPhoto downloadPhoto(Integer id, List<String> filters) {
        FilteredPhoto filteredPhoto = new FilteredPhoto(photoRepository.findById(id).get());
        return filterService.getFilteredPhoto(filteredPhoto, filters);
    }

}

