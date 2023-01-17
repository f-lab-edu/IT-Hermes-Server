package com.hermes.ithermes.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hermes.ithermes.application.SubscribeService;
import com.hermes.ithermes.domain.exception.WrongIdOrPasswordException;
import com.hermes.ithermes.domain.util.JobType;
import com.hermes.ithermes.presentation.dto.subscribe.SubscribePutSubscribeRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SubscribeController.class)
class SubscribeControllerTest {

    @MockBean
    private SubscribeService subscribeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("구독_PUT_정상처리")
    void 구독_PUT_정상처리() throws Exception {
        SubscribePutSubscribeRequestDto subscribePutSubscribeRequestDto = new SubscribePutSubscribeRequestDto("test",
                        new String[]{"ACTIVE","ACTIVE","NOT_ACTIVE","NOT_ACTIVE","NOT_ACTIVE","NOT_ACTIVE","NOT_ACTIVE"},
                        JobType.BACKEND,
                        "3",
                        "5");

        mockMvc.perform(put("/subscribe/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subscribePutSubscribeRequestDto)))
                .andExpect(status().isOk()); // 추후 201로 수정필요!
    }

    @Test
    @DisplayName("구독_PUT_실패처리")
    void 구독_PUT_실패처리() throws Exception {
        SubscribePutSubscribeRequestDto subscribePutSubscribeRequestDto = new SubscribePutSubscribeRequestDto("test",
                new String[]{"ACTIVE","ACTIVE","NOT_ACTIVE","NOT_ACTIVE","NOT_ACTIVE","NOT_ACTIVE","NOT_ACTIVE"},
                JobType.BACKEND,
                "3",
                "5");

        when(subscribeService.putSubscribe(any())).thenThrow(new WrongIdOrPasswordException());

        mockMvc.perform(put("/subscribe/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subscribePutSubscribeRequestDto)))
                .andExpect(status().isBadRequest());
    }
}