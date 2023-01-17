package com.hermes.ithermes.presentation.controller;

import com.hermes.ithermes.application.AlarmService;
import com.hermes.ithermes.presentation.dto.alarm.AlarmResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {

    @Value("${telegram-key}")
    private String telegramKey;

    private final AlarmService alarmService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ResponseEntity<AlarmResponseDto> alarm(String telegramKey){
        alarmService.alarm(telegramKey);
        AlarmResponseDto alarmResponseDto=new AlarmResponseDto("요청에 성공했습니다.");
        return ResponseEntity.ok(alarmResponseDto);
    }


}
