package com.vidakovic.nrakpo.service.criteria;

import com.vidakovic.nrakpo.controller.apimodel.PhotoApiModel;
import com.vidakovic.nrakpo.controller.form.CriteriaForm;
import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.data.repository.PhotoRepository;
import com.vidakovic.nrakpo.util.DateUtil;
import com.vidakovic.nrakpo.util.HashtagUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CriteriaService {

    PhotoRepository photoRepository;
    DateUtil dateUtil;
    HashtagUtil hashtagUtil;

    public CriteriaService(PhotoRepository photoRepository, DateUtil dateUtil, HashtagUtil hashtagUtil) {
        this.photoRepository = photoRepository;
        this.dateUtil = dateUtil;
        this.hashtagUtil = hashtagUtil;
    }

    public List<Photo> getPhotosByCriteria(CriteriaForm criteriaForm) {
        return new OrCriteria(
                new OrCriteria(
                        new AuthorCriteria(criteriaForm.getAuthor()),
                        new DateCriteria(criteriaForm.getDateFrom(),criteriaForm.getDateTo())
                ),
                new OrCriteria(
                        new SizeCriteria(criteriaForm.getSizeX()+"X"+criteriaForm.getSizeY()),
                        new HashtagCriteria(criteriaForm.getHashtags())
                )
        ).criteriaCheck((List<Photo>)photoRepository.findAll());
    }

    public List<PhotoApiModel> getPhotosByCriteriaUsingStream(CriteriaForm criteriaForm) {
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


}
