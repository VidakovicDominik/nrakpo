package com.vidakovic.nrakpo.service;

import com.vidakovic.nrakpo.controller.apimodel.CriteriaForm;
import com.vidakovic.nrakpo.controller.apimodel.FilteredPhoto;
import com.vidakovic.nrakpo.controller.apimodel.PhotoApiModel;
import com.vidakovic.nrakpo.data.entity.Hashtag;
import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.data.entity.User;
import com.vidakovic.nrakpo.data.entity.enums.ImageFormat;
import com.vidakovic.nrakpo.data.repository.HashtagRepository;
import com.vidakovic.nrakpo.data.repository.PhotoRepository;
import com.vidakovic.nrakpo.data.repository.UserRepository;
import com.vidakovic.nrakpo.service.cor.FilterService;
import com.vidakovic.nrakpo.service.criteria.CriteriaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotoServiceImpl implements PhotoService {

    PhotoRepository photoRepository;
    UserRepository userRepository;
    ConsumptionEvaluator consumptionEvaluator;
    HashtagRepository hashtagRepository;
    CriteriaService criteriaService;
    FilterService filterService;

    public PhotoServiceImpl(PhotoRepository photoRepository, UserRepository userRepository, ConsumptionEvaluator consumptionEvaluator, HashtagRepository hashtagRepository, CriteriaService criteriaService, FilterService filterService) {
        this.photoRepository = photoRepository;
        this.userRepository = userRepository;
        this.consumptionEvaluator = consumptionEvaluator;
        this.hashtagRepository = hashtagRepository;
        this.criteriaService = criteriaService;
        this.filterService = filterService;
    }

    @Override
    public PhotoApiModel getPhoto(Integer id) {
        return new PhotoApiModel(photoRepository.findById(id).get());
    }

    @Override
    public void insertPhoto(PhotoApiModel photo, String username) {

        User user = userRepository.findById(username).get();
        if (!consumptionEvaluator.evaluate(user, getMonthlyConsumption(user))) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "You can't upload any more pictures this month");
        }
        photoRepository.save(
                new Photo(
                        photo.getDescription(),
                        photo.getUrl(),
                        photo.getSizeX()+"X"+photo.getSizeY(),
                        ImageFormat.valueOf(photo.getFormat()),
                        parseHashtags(photo.getHashtags()),
                        user));
    }

    private List<Hashtag> parseHashtags(String rawHashtags) {
        List<Hashtag> hashtags = new ArrayList<>();
        for (String ht : rawHashtags.trim().split("#")) {
            if (!ht.equals(""))
                hashtags.add(new Hashtag(ht.trim()));
        }
        return hashtags;
    }

    @Override
    public Page<PhotoApiModel> getAllPhotos(Pageable pageable) {
        return photoRepository.findAll(pageable).map(this::parsePhoto);
    }

    private PhotoApiModel parsePhoto(Photo photo) {
        return new PhotoApiModel(photo);
    }

    @Override
    @Transactional
    public void updatePhoto(PhotoApiModel photo, String username) {
        Photo oldPhoto = photoRepository.findById(photo.getId()).get();
        hashtagRepository.deleteAll(oldPhoto.getHashtags());
        oldPhoto.setDescription(photo.getDescription());
        oldPhoto.setFormat(ImageFormat.valueOf(photo.getFormat()));
        oldPhoto.setUrl(photo.getUrl());
        oldPhoto.setSize(photo.getSizeX()+"X"+photo.getSizeY());
        oldPhoto.setHashtags(parseHashtags(photo.getHashtags()));
        photoRepository.save(oldPhoto);
    }


    @Override
    public Long getMonthlyConsumption(User user) {
        return photoRepository.countByUserAndDateBetween(user, new Date().getTime() - 2629743L, new Date().getTime());
    }

    @Override
    public List<PhotoApiModel> filterPhotos(CriteriaForm criteriaForm){
        return criteriaService.getPhotosByCriteria(criteriaForm).stream().map(x->new PhotoApiModel(x)).collect(Collectors.toList());
    }

    @Override
    public FilteredPhoto downloadPhoto(Integer id, List<String> filters) {
        FilteredPhoto filteredPhoto=new FilteredPhoto(photoRepository.findById(id).get());
        return filterService.getFilteredPhoto(filteredPhoto,filters);
    }
}

