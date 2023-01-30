package com.hermes.ithermes.presentation.controller;

import com.hermes.ithermes.application.UrlRecordService;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.urlrecord.UrlRecordPutViewCountRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/url-record")
public class UrlRecordController {
    private final UrlRecordService urlRecordService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> putViewCount(@RequestBody UrlRecordPutViewCountRequestDto urlRecordPutViewCountRequestDto, HttpServletRequest httpServletRequest) throws URISyntaxException {
        String ipAddress = httpServletRequest.getRemoteAddr();
        urlRecordService.putViewCount(urlRecordPutViewCountRequestDto, ipAddress);
        URI redirectUri = new URI(urlRecordPutViewCountRequestDto.getUrl());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
