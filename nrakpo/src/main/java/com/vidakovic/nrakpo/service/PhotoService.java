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
import com.vidakovic.nrakpo.service.strategy.PackageUsageService;
import com.vidakovic.nrakpo.util.DateUtil;
import com.vidakovic.nrakpo.util.HashtagUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotoService {

    PhotoRepository photoRepository;
    UserRepository userRepository;
    HashtagRepository hashtagRepository;
    CriteriaService criteriaService;
    FilterService filterService;
    UserService userService;
    PackageUsageService packageUsageService;
    HashtagUtil hashtagUtil;
    DateUtil dateUtil;
            

    public PhotoService(PhotoRepository photoRepository,
                        UserRepository userRepository,
                        HashtagRepository hashtagRepository,
                        CriteriaService criteriaService,
                        FilterService filterService,
                        PackageUsageService packageUsageService,
                        HashtagUtil hashtagUtil,
                        DateUtil dateUtil,
                        UserService userService) {
        this.photoRepository = photoRepository;
        this.userRepository = userRepository;
        this.hashtagRepository = hashtagRepository;
        this.criteriaService = criteriaService;
        this.filterService = filterService;
        this.packageUsageService = packageUsageService;
        this.hashtagUtil = hashtagUtil;
        this.dateUtil=dateUtil;
        this.userService=userService;
    }

    
    public PhotoApiModel getPhoto(Integer id) {
        return new PhotoApiModel(photoRepository.findById(id).get());
    }

    
    public void insertPhoto(PhotoApiModel photo, String username) {
        User user = userRepository.findById(username).get();
        if (userService.checkMonthlyConsumption(username,photoRepository.countByUserAndDateBetween(user, Date.from(ZonedDateTime.now().minusMonths(1).toInstant()).getTime(), new Date().getTime()))) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "You can't upload any more pictures this month");
        }
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
        List<Photo> photos = (List<Photo>) photoRepository.findAll();
        return photos.stream()
                .filter(x -> x.getUser().getUsername().equals(criteriaForm.getAuthor()))
                .filter(x -> x.getDate() > dateUtil.getLongDate(criteriaForm.getDateFrom()) && x.getDate() < dateUtil.getLongDate(criteriaForm.getDateTo()))
                .filter(x -> checkSize(criteriaForm, x.getSize()))
                .filter(x -> hashtagUtil.hasCommonHashtag(criteriaForm.getHashtags(), x.getHashtags()))
                .map(x -> new PhotoApiModel(x))
                .collect(Collectors.toList());
    }

    private boolean checkSize(CriteriaForm criteriaForm, String size) {
        String[] splitSize = size.split("X");
        return Integer.parseInt(splitSize[0]) == Integer.parseInt(criteriaForm.getSizeX())
                &&
                Integer.parseInt(splitSize[1]) == Integer.parseInt(criteriaForm.getSizeY());
    }

    
    public FilteredPhoto downloadPhoto(Integer id, List<String> filters) {
        FilteredPhoto filteredPhoto = new FilteredPhoto(photoRepository.findById(id).get());
        return filterService.getFilteredPhoto(filteredPhoto, filters);
    }

}

