package com.hermes.ithermes.presentation.controller;

import com.hermes.ithermes.application.CrawlingContentsLastUrlService;
import com.hermes.ithermes.presentation.dto.crawlingcontentslasttitle.CrawlingContentsLastUrlFindAllResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/crawling-contents-last-title")
public class CrawlingContentsLastUrlController {

    private final CrawlingContentsLastUrlService crawlingContentsLastUrlService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ResponseEntity<CrawlingContentsLastUrlFindAllResponseDto> findAllCrawlingContentsLastTitle() {
        CrawlingContentsLastUrlFindAllResponseDto crawlingContentsLastUrlFindAllResponseDto = crawlingContentsLastUrlService.findAllCrawlingContentsLastTitle();
        return ResponseEntity.ok(crawlingContentsLastUrlFindAllResponseDto);
    }
}
