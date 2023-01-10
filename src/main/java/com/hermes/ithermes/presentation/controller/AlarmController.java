package com.hermes.ithermes.presentation.controller;

import com.hermes.ithermes.application.AlarmService;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.alarm.AlarmFindSubscribeRequestDto;
import com.hermes.ithermes.presentation.dto.alarm.AlarmFindSubscribeResponseDto;
import com.hermes.ithermes.presentation.dto.alarm.AlarmUpdateSubscribeRequestDto;
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

    @RequestMapping(value = "/subscribe", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponseDto> updateSubscribe(@Valid @RequestBody AlarmUpdateSubscribeRequestDto alarmUpdateSubScribeActiveRequestDto) {

        CommonResponseDto commonResponseDto = alarmService.updateSubscribe(alarmUpdateSubScribeActiveRequestDto);
        return ResponseEntity.ok(commonResponseDto);
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public ResponseEntity<AlarmFindSubscribeResponseDto> findSubscribe(@Valid @RequestBody AlarmFindSubscribeRequestDto alarmFindSubscribeRequestDto) {

        AlarmFindSubscribeResponseDto alarmFindSubscribeResponseDto = alarmService.findSubscribe(alarmFindSubscribeRequestDto);
        return ResponseEntity.ok(alarmFindSubscribeResponseDto);
    }
}
