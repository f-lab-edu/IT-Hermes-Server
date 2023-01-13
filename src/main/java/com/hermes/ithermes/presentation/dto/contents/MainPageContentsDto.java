package com.hermes.ithermes.presentation.dto.contents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class MainPageContentsDto {

    public String title;

    public String image;

    public String url;

    public String category;

    public String service;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime contentsDate;

    public static MainPageContentsDto YoutubeAndNewsEntityToDto(YoutubeAndNews youtubeAndNews){
        return MainPageContentsDto.builder()
                .title(youtubeAndNews.getTitle())
                .image(youtubeAndNews.getImage())
                .url(youtubeAndNews.getUrl())
                .category(youtubeAndNews.getService().getCategory().getName())
                .service(youtubeAndNews.getService().getName())
                .contentsDate(youtubeAndNews.getContentsDate())
                .build();
    }

    public static MainPageContentsDto JobEntityToDto(Job job){
        return MainPageContentsDto.builder()
                .title(job.getTitle())
                .image(null)
                .url(job.getUrl())
                .category(job.getService().getCategory().getName())
                .service(job.getService().getName())
                .contentsDate(job.getEndDate())
                .build();
    }

}
