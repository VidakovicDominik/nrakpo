package com.vidakovic.nrakpo.data.repository;

import com.vidakovic.nrakpo.data.entity.User;

public interface UserRepositoryCustom {
    void saveUserWithAuthorities(User user);
}
