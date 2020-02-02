package com.vidakovic.nrakpo.controller;

import com.vidakovic.nrakpo.controller.apimodel.UserApiModel;
import com.vidakovic.nrakpo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest extends ControllerTest{

    @MockBean
    UserService userService;

    @Test
    void showUsers() throws Exception {
        when(userService.getUsers()).thenReturn(Collections.emptyList());
        mvc.perform(get("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("Users")))
                .andExpect(status().isOk());
    }

    @Test
    void showUpdateUser() throws Exception {
        when(userService.getUser(any())).thenReturn(new UserApiModel());
        mvc.perform(get("/users/update/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("Update")))
                .andExpect(status().isOk());
    }

    @Test
    void updateUser() throws Exception {
        doNothing().when(userService).updateUser(any());
        mvc.perform(post("/users/update")
                .content(objectMapper.writeValueAsString(new UserApiModel()))
        )
                .andExpect(status().is3xxRedirection());
    }
}
