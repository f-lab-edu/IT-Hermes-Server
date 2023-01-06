package com.hermes.ithermes.presentation.controller;

import com.hermes.ithermes.application.ContentsService;
import com.hermes.ithermes.presentation.dto.contents.MainContentsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contents")
public class ContentsController {

    private final ContentsService contentsService;

    @Autowired
    public ContentsController(ContentsService contentsService) {
        this.contentsService = contentsService;
    }

    @RequestMapping(value = "/main",method = RequestMethod.GET)
    public ResponseEntity<List<MainContentsDto>> getMainContents(){
        return ResponseEntity.ok(contentsService.getMainContents());
    }

}
