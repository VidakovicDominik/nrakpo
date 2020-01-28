package com.vidakovic.nrakpo.service.criteria;

import com.vidakovic.nrakpo.controller.form.CriteriaForm;
import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.data.repository.PhotoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CriteriaService {

    PhotoRepository photoRepository;

    public CriteriaService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
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
}
