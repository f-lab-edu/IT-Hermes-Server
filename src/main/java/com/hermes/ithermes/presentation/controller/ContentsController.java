package com.hermes.ithermes.presentation.controller;

import com.hermes.ithermes.application.ContentsService;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.presentation.dto.contents.ContentsDto;
import com.hermes.ithermes.presentation.dto.contents.MainContentsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contents")
@RequiredArgsConstructor
public class ContentsController {

    private final ContentsService contentsService;

    @RequestMapping(value = "/main",method = RequestMethod.GET)
    public ResponseEntity<List<MainContentsDto>> getMainContents(@RequestParam(value = "type")String type){
        return ResponseEntity.ok(contentsService.getMainContents(type));
    }

    @RequestMapping(value = "/category",method = RequestMethod.GET)
    public ResponseEntity<List<ContentsDto>> getCategoryContents(@RequestParam(value = "type")String type,@RequestParam(value = "page")int page,
                                                                 @RequestParam(value = "order",required = false)String order){
        return ResponseEntity.ok(contentsService.getCategoryContents(type,page,order));
    }

}
