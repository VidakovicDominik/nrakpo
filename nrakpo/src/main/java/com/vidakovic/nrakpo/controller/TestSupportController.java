package com.vidakovic.nrakpo.controller;

import com.vidakovic.nrakpo.service.TestSupportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test-support")
public class TestSupportController {

    TestSupportService testSupportService;

    public TestSupportController(TestSupportService testSupportService) {
        this.testSupportService = testSupportService;
    }

    @GetMapping("/mock")
    public String mockDummyPhotos(Model model) {
        testSupportService.mock();
        return "redirect:/home";
    }
}

