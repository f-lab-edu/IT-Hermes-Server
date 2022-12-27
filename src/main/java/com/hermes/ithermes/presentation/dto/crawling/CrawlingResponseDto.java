package com.hermes.ithermes.presentation.dto.crawling;

import lombok.Getter;

@Getter
public class CrawlingResponseDto {

    private long id;

    private String title;

    private String content;

    private String image;

    private String url;

    private int referenceCnt;

}
