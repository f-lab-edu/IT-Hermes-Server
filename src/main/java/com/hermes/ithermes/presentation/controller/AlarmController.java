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

    @RequestMapping(value = "/subscription",method = RequestMethod.GET)
    public ResponseEntity<CommonResponseDto> alarmSubscribeContents(){
        return ResponseEntity.ok(alarmService.sendSubscribeAlarm());
    }

    @RequestMapping(value = "/recommend",method = RequestMethod.GET)
    public ResponseEntity<CommonResponseDto> alarmRecommendContents(){
        return ResponseEntity.ok(alarmService.sendRecommendAlarm());
    }

}
