package com.vidakovic.nrakpo.service;

import com.vidakovic.nrakpo.controller.apimodel.PhotoApiModel;
import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.data.repository.PhotoRepository;
import com.vidakovic.nrakpo.data.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * <b>Title: PhotoServiceImpl  </b>
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
 * PA1 22-Aug-19
 * @since 22-Aug-19 10:21:42
 */
@Service
public class PhotoServiceImpl implements PhotoService {

    PhotoRepository photoRepository;
    UserRepository userRepository;

    public PhotoServiceImpl(PhotoRepository photoRepository, UserRepository userRepository){
        this.photoRepository=photoRepository;
        this.userRepository=userRepository;
    }


    @Override
    public void insertPhoto(PhotoApiModel photo, String username) {
        photoRepository.save(new Photo(photo,userRepository.findById(username).get()));
    }

    @Override
    public List<PhotoApiModel> getAllPhotos() {
        return parsePhotos(photoRepository.findAll());
    }

    private List<PhotoApiModel> parsePhotos(Iterable<Photo> photos){
        List<PhotoApiModel> photoApiModels=new ArrayList<>();
        for (Photo photo :
                photos) {
            photoApiModels.add(new PhotoApiModel(photo));
        }
        return photoApiModels;
    }

    @Override
    public void updatePhoto(PhotoApiModel photo, String username) {
        photoRepository.save(new Photo(photo,userRepository.findById(username).get()));
    }
}

