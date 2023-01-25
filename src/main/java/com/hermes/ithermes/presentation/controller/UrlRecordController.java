package com.hermes.ithermes.presentation.controller;

import com.hermes.ithermes.application.UrlRecordService;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.urlrecord.UrlRecordPutViewCountRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/url-record")
public class UrlRecordController {
    private final UrlRecordService urlRecordService;

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> putViewCount(@RequestBody UrlRecordPutViewCountRequestDto urlRecordPutViewCountRequestDto){
        String viewCountUrl = urlRecordService.putViewCount(urlRecordPutViewCountRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"+viewCountUrl));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
