package com.vidakovic.nrakpo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class ControllerTest {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

}
