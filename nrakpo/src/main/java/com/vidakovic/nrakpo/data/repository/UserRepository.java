package com.vidakovic.nrakpo.data.repository;

import com.vidakovic.nrakpo.data.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String>, UserRepositoryCustom {
}
