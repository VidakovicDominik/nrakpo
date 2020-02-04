package com.vidakovic.nrakpo.controller;

import com.vidakovic.nrakpo.controller.form.RegistrationForm;
import com.vidakovic.nrakpo.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegistrationController.class)
class RegistrationControllerTest extends ControllerTest {

    @MockBean
    UserServiceImpl userServiceImpl;

    @Test
    void showRegisterForm() throws Exception {
        mvc.perform(get("/register")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("Registration")))
                .andExpect(status().isOk());
    }

    @Test
    void processRegistration() throws Exception {
        doNothing().when(userServiceImpl).insertUser(any());
        mvc.perform(post("/register")
                .content(objectMapper.writeValueAsString(new RegistrationForm()))
        )
                .andExpect(status().is3xxRedirection());
    }
}
