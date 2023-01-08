package com.hermes.ithermes.presentation.controller;

import com.hermes.ithermes.application.ContentsService;
import com.hermes.ithermes.infrastructure.YoutubeAndNewRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewRepository.*;
import com.hermes.ithermes.presentation.dto.contents.ContentsDto;
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
    public ResponseEntity<List<MainContents>> getMainContents(){
        return ResponseEntity.ok(contentsService.getMainContents());
    }

    @RequestMapping(value = "/category",method = RequestMethod.GET)
    public ResponseEntity<List<ContentsDto>> getCategoryContents(@RequestParam(value = "type",required = false)String type){
        return ResponseEntity.ok(contentsService.getCategoryContents(type));
    }

}
