package com.vidakovic.nrakpo.service;

import com.vidakovic.nrakpo.controller.form.CriteriaForm;
import com.vidakovic.nrakpo.controller.apimodel.FilteredPhoto;
import com.vidakovic.nrakpo.controller.apimodel.PhotoApiModel;
import com.vidakovic.nrakpo.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <p>
 * <b>Title: PhotoService  </b>
 * </p>
 * <p>
 * <b> Description:
 *
 *
 * </b>
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) ETK 2019
 * </p>
 * <p>
 * <b>Company:</b> Ericsson Nikola Tesla d.d.
 * </p>
 *
 * @author ezviddo
 * @version PA1
 * <p>
 * <b>Version History:</b>
 * </p>
 * <br>
 * PA1 21-Aug-19
 * @since 21-Aug-19 10:43:42
 */
public interface PhotoService {
    PhotoApiModel getPhoto(Integer id);
    void insertPhoto(PhotoApiModel photo, String username);
    Page<PhotoApiModel> getAllPhotos(Pageable pageable);
    void updatePhoto(PhotoApiModel photo, String username);
    boolean checkMonthlyConsumption(User user);
    List<PhotoApiModel> filterPhotos(CriteriaForm criteriaForm);
    FilteredPhoto downloadPhoto(Integer id, List<String> filters);
    void mock();
}
