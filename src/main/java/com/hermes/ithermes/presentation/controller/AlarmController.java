package com.hermes.ithermes.presentation.controller;

import com.hermes.ithermes.application.AlarmService;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ResponseEntity<CommonResponseDto> alarm(){
        return ResponseEntity.ok(alarmService.alarm());
    }

}
