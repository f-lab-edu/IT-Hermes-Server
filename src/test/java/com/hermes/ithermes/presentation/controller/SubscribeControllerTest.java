package com.hermes.ithermes.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hermes.ithermes.application.SubscribeService;
import com.hermes.ithermes.domain.exception.WrongIdOrPasswordException;
import com.hermes.ithermes.presentation.dto.subscribe.SubscribeContentsDto;
import com.hermes.ithermes.presentation.dto.subscribe.SubscribeFindSubscribeRequestDto;
import com.hermes.ithermes.presentation.dto.subscribe.SubscribePutSubscribeRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SubscribeController.class)
@AutoConfigureDataJpa
class SubscribeControllerTest {

    @MockBean
    private SubscribeService subscribeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("구독_PUT_정상처리")
    @WithMockUser
    void 구독_PUT_정상처리() throws Exception {
        ArrayList<SubscribeContentsDto> subscribeContentsList = new ArrayList<>();
        subscribeContentsList.add(new SubscribeContentsDto("SARAMIN","ACTIVE"));
        subscribeContentsList.add(new SubscribeContentsDto("WANTED","ACTIVE"));
        subscribeContentsList.add(new SubscribeContentsDto("CODING_WORLD","ACTIVE"));
        subscribeContentsList.add(new SubscribeContentsDto("NAVER","ACTIVE"));
        subscribeContentsList.add(new SubscribeContentsDto("YOZM","ACTIVE"));
        subscribeContentsList.add(new SubscribeContentsDto("NOMAD_CODERS","NON_ACTIVE"));
        subscribeContentsList.add(new SubscribeContentsDto("DREAM_CODING","ACTIVE"));

        SubscribePutSubscribeRequestDto subscribePutSubscribeRequestDto = new SubscribePutSubscribeRequestDto("test",subscribeContentsList);

        mockMvc.perform(put("/api/subscribe/")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subscribePutSubscribeRequestDto)))
                .andExpect(status().isOk()); // 추후 201로 수정필요!
    }

    @Test
    @DisplayName("구독_PUT_실패처리")
    @WithMockUser
    void 구독_PUT_실패처리() throws Exception {
        ArrayList<SubscribeContentsDto> subscribeContentsList = new ArrayList<>();
        subscribeContentsList.add(new SubscribeContentsDto("SARAMIN","ACTIVE"));
        subscribeContentsList.add(new SubscribeContentsDto("WANTED","ACTIVE"));
        subscribeContentsList.add(new SubscribeContentsDto("CODING_WORLD","ACTIVE"));
        subscribeContentsList.add(new SubscribeContentsDto("NAVER","ACTIVE"));
        subscribeContentsList.add(new SubscribeContentsDto("YOZM","ACTIVE"));
        subscribeContentsList.add(new SubscribeContentsDto("NOMAD_CODERS","NON_ACTIVE"));
        subscribeContentsList.add(new SubscribeContentsDto("DREAM_CODING","ACTIVE"));

        SubscribePutSubscribeRequestDto subscribePutSubscribeRequestDto = new SubscribePutSubscribeRequestDto("test",subscribeContentsList);

        when(subscribeService.putSubscribe(any())).thenThrow(new WrongIdOrPasswordException());

        mockMvc.perform(put("/api/subscribe/")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subscribePutSubscribeRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("구독조회_성공처리")
    @WithMockUser
    void 구독조회_성공처리() throws Exception {
        SubscribeFindSubscribeRequestDto subscribeFindSubscribeRequestDto = new SubscribeFindSubscribeRequestDto("test");

        mockMvc.perform(post("/api/subscribe/")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subscribeFindSubscribeRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("구독조회_실패처리")
    @WithMockUser
    void 구독조회_실패처리() throws Exception {
        SubscribeFindSubscribeRequestDto subscribeFindSubscribeRequestDto = new SubscribeFindSubscribeRequestDto("test");

        when(subscribeService.findSubscribe(any())).thenThrow(new WrongIdOrPasswordException());

        mockMvc.perform(post("/api/subscribe/")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subscribeFindSubscribeRequestDto)))
                .andExpect(status().isBadRequest());
    }
}