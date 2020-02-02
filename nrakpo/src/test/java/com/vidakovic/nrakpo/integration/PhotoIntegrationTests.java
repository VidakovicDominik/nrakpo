package com.vidakovic.nrakpo.integration;

import com.vidakovic.nrakpo.controller.PhotoController;
import com.vidakovic.nrakpo.controller.form.RegistrationForm;
import com.vidakovic.nrakpo.data.entity.Hashtag;
import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.data.entity.enums.AccountType;
import com.vidakovic.nrakpo.data.entity.enums.ImageFormat;
import com.vidakovic.nrakpo.data.entity.enums.UserPackage;
import com.vidakovic.nrakpo.data.entity.enums.UserType;
import com.vidakovic.nrakpo.data.repository.PhotoRepository;
import com.vidakovic.nrakpo.data.repository.UserRepository;
import com.vidakovic.nrakpo.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PhotoController.class)
public class PhotoIntegrationTests {

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    MockMvc mvc;

    @BeforeAll
    @Transactional
    public void createUser() {
         userService.insertUser(new RegistrationForm("user", "pass", "email", UserType.USER, UserPackage.GOLD, AccountType.LOCAL));
    }

    @Test
    public void getPhotoDetailsTest() throws Exception {
        createPhoto();
        mvc.perform(get("/photo/1")
                .with(user("user").password("pass").roles("ADMINISTRATOR"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        
    }

    @Transactional
    private void createPhoto(){
        photoRepository.save(new Photo("description","url","50X50", ImageFormat.JPEG, Arrays.asList(new Hashtag("hashtag")),userRepository.findById("username").get()));
    }
}

