package com.vidakovic.nrakpo.scheduled;

import com.vidakovic.nrakpo.aspect.MeasureTime;
import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.data.entity.User;
import com.vidakovic.nrakpo.data.repository.PhotoRepository;
import com.vidakovic.nrakpo.data.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <p>
 * <b>Title: DatabaseMetricProvider  </b>
 * </p>
 * <p>
 * <b> Description:
 *
 *
 * </b>
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) ETK 2020
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
 * PA1 03-Feb-20
 * @since 03-Feb-20 17:20:42
 */
@Component
public class DatabaseMetricProvider {

    PhotoRepository photoRepository;
    UserRepository userRepository;

    public DatabaseMetricProvider(PhotoRepository photoRepository, UserRepository userRepository) {
        this.photoRepository = photoRepository;
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "*/5 * * * * *")
    @MeasureTime(metricName = "photo_repo_timer")
    public void checkPhotoRepoSpeed(){
        for (Photo photo:
                photoRepository.findAll()) {
            photo.getHashtags();
        }
    }

    @Scheduled(cron = "*/5 * * * * *")
    @MeasureTime(metricName = "user_repo_timer")
    public void checkUserRepoSpeed(){
        for (User user:
                userRepository.findAll()) {
            user.getUsername();
        }
    }
}

