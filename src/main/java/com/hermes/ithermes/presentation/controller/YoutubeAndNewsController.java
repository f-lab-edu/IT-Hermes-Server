package com.hermes.ithermes.presentation.controller;

import com.hermes.ithermes.application.YoutubeAndNewsService;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.youtubeandnews.YoutubeAndNewsCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/youtube-and-news")
public class YoutubeAndNewsController {
    private final YoutubeAndNewsService youtubeAndNewsService;

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> insertYoutubeAndNewsData(@RequestBody List<YoutubeAndNewsCreateRequestDto> youtubeAndNewsCreateRequestDtoList){
        CommonResponseDto commonResponseDto = youtubeAndNewsService.insertYoutubeAndNews(youtubeAndNewsCreateRequestDtoList);
        return ResponseEntity.ok(commonResponseDto);
    }
}
