package com.vidakovic.nrakpo.controller;

import com.vidakovic.nrakpo.controller.apimodel.FilteredPhoto;
import com.vidakovic.nrakpo.controller.apimodel.PhotoApiModel;
import com.vidakovic.nrakpo.controller.form.CriteriaForm;
import com.vidakovic.nrakpo.service.PhotoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PhotoController.class)
class PhotoControllerTest extends ControllerTest{

    @MockBean
    PhotoServiceImpl photoServiceImpl;

    @Test
    void showPhotoDetails() throws Exception {
        when(photoServiceImpl.getPhoto(any())).thenReturn(getPhotoApiModel());
        mvc.perform(get("/photo/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void showUpdatePhoto() throws Exception {
        when(photoServiceImpl.getPhoto(any())).thenReturn(getPhotoApiModel());
        mvc.perform(get("/photo/update/1")
                .with(user("admin").password("password").roles("ADMINISTRATOR"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void filterPhotos() throws Exception {
        when(photoServiceImpl.filterPhotos(any())).thenReturn(Collections.emptyList());
        mvc.perform(post("/photo/filter")
                .content(objectMapper.writeValueAsString(new CriteriaForm("author","hashtags","sizex","sizey","date","date")))
                .with(user("admin").password("password").roles("ADMINISTRATOR"))
        )
                .andDo(print())
                .andExpect(content().string(containsString("Filtered photos")))
                .andExpect(status().isOk());
    }

    @Test
    void updatePhoto() throws Exception {
        doNothing().when(photoServiceImpl).updatePhoto(any());
        mvc.perform(post("/photo/update")
                .content(objectMapper.writeValueAsString(getPhotoApiModel()))
                .with(user("admin").password("password").roles("ADMINISTRATOR"))
        )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void downloadAndFilterPhoto() throws Exception {
        when(photoServiceImpl.downloadPhoto(any(),any())).thenReturn(new FilteredPhoto());
        mvc.perform(post("/photo/download/1")
                .requestAttr("pickedFilters","[a,b,c]")
                .with(user("admin").password("password").roles("ADMINISTRATOR"))
        )
                .andDo(print())
                .andExpect(content().string(containsString("Please")))
                .andExpect(status().isOk());
    }

    private PhotoApiModel getPhotoApiModel() {
        return new PhotoApiModel(1, "description", "url", "sizex", "sizey", "format", "hashtags", "date", "username");
    }
}
