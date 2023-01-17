package com.hermes.ithermes.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hermes.ithermes.application.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

class SubscribeControllerTest {

    @MockBean
    private SubscribeService subscribeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
}