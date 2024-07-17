package com.hermes.ithermes.presentation.dto.youtubeandnews;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class YoutubeAndNewsCrawlingDto {
    private String title;
    private String url;
    private String description;
    private String thumbnail;
    private String date;
}