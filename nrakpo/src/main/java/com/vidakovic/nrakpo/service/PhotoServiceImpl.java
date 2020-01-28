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
import com.vidakovic.nrakpo.service.builder.PhotoBuilder;
import com.vidakovic.nrakpo.service.builder.PhotoMockDirector;
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

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotoServiceImpl implements PhotoService {

    PhotoRepository photoRepository;
    UserRepository userRepository;
    HashtagRepository hashtagRepository;
    CriteriaService criteriaService;
    FilterService filterService;
    PackageUsageService packageUsageService;
    HashtagUtil hashtagUtil;
    DateUtil dateUtil;
            

    public PhotoServiceImpl(PhotoRepository photoRepository,
                            UserRepository userRepository,
                            HashtagRepository hashtagRepository,
                            CriteriaService criteriaService,
                            FilterService filterService,
                            PackageUsageService packageUsageService,
                            HashtagUtil hashtagUtil,
                            DateUtil dateUtil) {
        this.photoRepository = photoRepository;
        this.userRepository = userRepository;
        this.hashtagRepository = hashtagRepository;
        this.criteriaService = criteriaService;
        this.filterService = filterService;
        this.packageUsageService = packageUsageService;
        this.hashtagUtil = hashtagUtil;
        this.dateUtil=dateUtil;
    }

    @Override
    public PhotoApiModel getPhoto(Integer id) {
        return new PhotoApiModel(photoRepository.findById(id).get());
    }

    @Override
    public void insertPhoto(PhotoApiModel photo, String username) {
        User user = userRepository.findById(username).get();
        if (checkMonthlyConsumption(user)) {
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

    @Override
    public Page<PhotoApiModel> getAllPhotos(Pageable pageable) {
        return photoRepository.findAll(pageable).map(PhotoApiModel::new);
    }

    @Override
    @Transactional
    public void updatePhoto(PhotoApiModel photo, String username) {
        Photo oldPhoto = photoRepository.findById(photo.getId()).get();
        hashtagRepository.deleteAll(oldPhoto.getHashtags());
        oldPhoto.setDescription(photo.getDescription());
        oldPhoto.setFormat(ImageFormat.valueOf(photo.getFormat()));
        oldPhoto.setUrl(photo.getUrl());
        oldPhoto.setSize(photo.getSizeX() + "X" + photo.getSizeY());
        oldPhoto.setHashtags(hashtagUtil.parseHashtagsToList(photo.getHashtags()));
        photoRepository.save(oldPhoto);
    }

    @Override
    public boolean checkMonthlyConsumption(User user) {
        return packageUsageService.exeededLimit(user, photoRepository.countByUserAndDateBetween(user, new Date().getTime() - 2629743L, new Date().getTime()));
    }

    @Override
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

    @Override
    public FilteredPhoto downloadPhoto(Integer id, List<String> filters) {
        FilteredPhoto filteredPhoto = new FilteredPhoto(photoRepository.findById(id).get());
        return filterService.getFilteredPhoto(filteredPhoto, filters);
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

