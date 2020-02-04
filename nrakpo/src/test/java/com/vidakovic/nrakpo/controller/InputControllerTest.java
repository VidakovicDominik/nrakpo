package com.vidakovic.nrakpo.controller;

import com.vidakovic.nrakpo.controller.apimodel.PhotoApiModel;
import com.vidakovic.nrakpo.service.PhotoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InputController.class)
class InputControllerTest extends ControllerTest {

    @MockBean
    PhotoServiceImpl photoServiceImpl;

    @Test
    void pictureUpload() throws Exception {
        doNothing().when(photoServiceImpl).insertPhoto(any(),any());
        mvc.perform(post("/pictureUpload")
                .content(objectMapper.writeValueAsString(new PhotoApiModel(1,"description","url","sizex","sizey","format","hashtags","date","username")))
                .with(user("admin").password("password").roles("ADMINISTRATOR"))
        )
                .andExpect(status().is3xxRedirection());
    }
}
