package com.vidakovic.nrakpo.data.repository;

import com.vidakovic.nrakpo.data.entity.Hashtag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * <p>
 * <b>Title: HashtagRepository  </b>
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
 * @since 22-Aug-19 15:09:42
 */
public interface HashtagRepository extends CrudRepository<Hashtag,Integer> {
    List<Hashtag> findByNameIn(List<String> names);
}

