package com.vidakovic.nrakpo.service.criteria;

import com.vidakovic.nrakpo.controller.apimodel.CriteriaForm;
import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.data.repository.PhotoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterService {

    PhotoRepository photoRepository;

    public FilterService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public List<Photo> getFilteredPhotos(CriteriaForm criteriaForm) {
        List<Photo> allPhotos=(List<Photo>)photoRepository.findAll();
        return new OrCriteria(
                new OrCriteria(
                        new AuthorFilter(criteriaForm.getAuthor()),
                        new DateFilter(criteriaForm.getDateFrom(),criteriaForm.getDateTo())
                ),
                new OrCriteria(
                        new SizeFilter(criteriaForm.getSizeX()+"X"+criteriaForm.getSizeY()),
                        new HashtagFilter(criteriaForm.getHashtags())
                )
        ).filterPhotos(allPhotos);
    }
}
