package com.hermes.ithermes.presentation.controller;

import com.hermes.ithermes.application.AlarmService;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.alarm.AlarmFindAlarmRequestDto;
import com.hermes.ithermes.presentation.dto.alarm.AlarmFindAlarmResponseDto;
import com.hermes.ithermes.presentation.dto.alarm.AlarmPutAlarmRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/alarm")
public class AlarmController {
    private final AlarmService alarmService;

    @Autowired
    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponseDto> putAlarm(@Valid @RequestBody AlarmPutAlarmRequestDto alarmPutAlarmRequestDto) {

        CommonResponseDto commonResponseDto = alarmService.putAlarm(alarmPutAlarmRequestDto);
        return ResponseEntity.ok(commonResponseDto);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<AlarmFindAlarmResponseDto> findAlarm(@Valid @RequestBody AlarmFindAlarmRequestDto alarmFindSubscribeRequestDto) {

        AlarmFindAlarmResponseDto alarmFindSubscribeResponseDto = alarmService.findAlarm(alarmFindSubscribeRequestDto);
        return ResponseEntity.ok(alarmFindSubscribeResponseDto);
    }
}
