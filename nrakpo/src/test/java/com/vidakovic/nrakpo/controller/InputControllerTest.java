package com.vidakovic.nrakpo.controller;

import com.vidakovic.nrakpo.controller.apimodel.PhotoApiModel;
import com.vidakovic.nrakpo.service.PhotoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InputController.class)
class InputControllerTest extends ControllerTest {

    @MockBean
    PhotoService photoService;

    @Test
    void showPictureUpload() throws Exception {
        mvc.perform(get("/pictureUpload")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Picture Upload")));
    }

    @Test
    void pictureUpload() throws Exception {
        doNothing().when(photoService).insertPhoto(any(),any());
        mvc.perform(post("/pictureUpload")
                .content(objectMapper.writeValueAsString(new PhotoApiModel(1,"description","url","sizex","sizey","format","hashtags","date","username")))
                .with(user("admin").password("password").roles("ADMINISTRATOR"))
        )
                .andExpect(status().is3xxRedirection());
    }
}
