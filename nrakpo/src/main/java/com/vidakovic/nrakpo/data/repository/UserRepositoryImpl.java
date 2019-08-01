/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vidakovic.nrakpo.data.repository;

import com.vidakovic.nrakpo.data.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author matij
 */
@Repository
public class UserRepositoryImpl implements UserRepositoryCustom{

    @PersistenceContext
    EntityManager em;
    
    @Transactional
    @Override
    public void saveUserWithAuthorities(User user) {
            em.persist(user);
        em.createNativeQuery("INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ( ?1 , 'ROLE_USER')")
                .setParameter(1, user.getUsername())
                .executeUpdate();
    }
    
}
