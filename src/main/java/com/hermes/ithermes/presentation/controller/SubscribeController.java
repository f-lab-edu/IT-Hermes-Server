package com.hermes.ithermes.presentation.controller;

import com.hermes.ithermes.application.SubscribeService;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.subscribe.SubscribeFindSubscribeRequestDto;
import com.hermes.ithermes.presentation.dto.subscribe.SubscribeFindSubscribeResponseDto;
import com.hermes.ithermes.presentation.dto.subscribe.SubscribePutSubscribeRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/subscribe")
public class SubscribeController {
    private final SubscribeService subscribeService;

    @Autowired
    public SubscribeController(SubscribeService subscribeService) {
        this.subscribeService = subscribeService;
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponseDto> putSubscribe(@Valid @RequestBody SubscribePutSubscribeRequestDto subscribePutSubscribeRequestDto) {

        CommonResponseDto commonResponseDto = subscribeService.putSubscribe(subscribePutSubscribeRequestDto);
        return ResponseEntity.ok(commonResponseDto);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<SubscribeFindSubscribeResponseDto> findSubscribe(@Valid @RequestBody SubscribeFindSubscribeRequestDto subscribeFindSubscribeRequestDto) {

        SubscribeFindSubscribeResponseDto subscribeFindSubscribeResponseDto = subscribeService.findSubscribe(subscribeFindSubscribeRequestDto);
        return ResponseEntity.ok(subscribeFindSubscribeResponseDto);
    }
}
