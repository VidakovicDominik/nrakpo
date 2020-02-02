package com.vidakovic.nrakpo.integration;

import com.vidakovic.nrakpo.controller.form.RegistrationForm;
import com.vidakovic.nrakpo.data.entity.Hashtag;
import com.vidakovic.nrakpo.data.entity.Photo;
import com.vidakovic.nrakpo.data.entity.enums.AccountType;
import com.vidakovic.nrakpo.data.entity.enums.ImageFormat;
import com.vidakovic.nrakpo.data.entity.enums.UserPackage;
import com.vidakovic.nrakpo.data.entity.enums.UserType;
import com.vidakovic.nrakpo.data.repository.PhotoRepository;
import com.vidakovic.nrakpo.data.repository.UserRepository;
import com.vidakovic.nrakpo.service.PhotoService;
import com.vidakovic.nrakpo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PhotoIntegrationTests {

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    PhotoService photoService;

    @Autowired
    MockMvc mvc;

    @BeforeEach
    public void createUser() {
        userRepository.deleteAll();

    }

    @Test
    public void getPhotoDetailsTest() throws Exception {
        userService.insertUser(new RegistrationForm("testuser", "pass", "email", UserType.USER, UserPackage.GOLD, AccountType.LOCAL));
        photoRepository.save(new Photo("description123","url","50X50", ImageFormat.JPEG, Arrays.asList(new Hashtag("hashtag")),userRepository.findById("testuser").get())).getId();
        mvc.perform(get("/photo/1")
                .with(user("testuser").password("pass").roles("ADMINISTRATOR"))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("testuser")))
                .andExpect(content().string(containsString("description123")))
                .andExpect(status().isOk());
    }


}

