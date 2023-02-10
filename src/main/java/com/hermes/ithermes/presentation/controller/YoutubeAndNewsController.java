package com.hermes.ithermes.presentation.controller;

import com.hermes.ithermes.application.YoutubeAndNewsService;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.youtubeandnews.YoutubeAndNewsInsertDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/youtube-and-news")
public class YoutubeAndNewsController {
    private final YoutubeAndNewsService youtubeAndNewsService;

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> parseYoutubeAndNews(@RequestBody YoutubeAndNewsInsertDto youtubeAndNewsCrawlingDtoList){
        CommonResponseDto commonResponseDto = youtubeAndNewsService.insertYoutubeAndNews(youtubeAndNewsCrawlingDtoList);
        return ResponseEntity.created(URI.create("/api/youtube-and-news")).body(commonResponseDto);
    }
}
