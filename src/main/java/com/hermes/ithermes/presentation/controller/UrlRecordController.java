package com.hermes.ithermes.presentation.controller;

import com.hermes.ithermes.application.UrlRecordService;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.urlrecord.UrlRecordPutViewCountRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/url-record")
public class UrlRecordController {
    private final UrlRecordService urlRecordService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> putViewCount(@RequestBody UrlRecordPutViewCountRequestDto urlRecordPutViewCountRequestDto, HttpServletRequest httpServletRequest) throws URISyntaxException {
        String ipAddress = httpServletRequest.getRemoteAddr();
        CommonResponseDto responseDto = urlRecordService.putViewCount(urlRecordPutViewCountRequestDto, ipAddress);
        return ResponseEntity.ok(responseDto);
    }
}
