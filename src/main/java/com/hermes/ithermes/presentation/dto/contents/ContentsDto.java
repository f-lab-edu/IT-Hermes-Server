package com.hermes.ithermes.presentation.dto.contents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ContentsDto {

    public String title;

    public String image;

    public String url;

    public String category;

    public String service;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime contentsDate;

    private String description;

    public static ContentsDto YoutubeAndNewsToContentsDto(YoutubeAndNews youtubeAndNews){
        return ContentsDto.builder()
                .title(youtubeAndNews.getTitle())
                .image(youtubeAndNews.getImage())
                .url(youtubeAndNews.getUrl())
                .category(youtubeAndNews.getService().getCategory().getName())
                .service(youtubeAndNews.getService().getName())
                .contentsDate(youtubeAndNews.getContentsDate())
                .description(youtubeAndNews.getDescription())
                .build();
    }

    public static ContentsDto JobToContentsDto(Job job){
        return ContentsDto.builder()
                .title(job.getTitle())
                .image(null)
                .url(job.getUrl())
                .category(job.getService().getCategory().getName())
                .service(job.getService().getName())
                .contentsDate(job.getEndDate())
                .description(job.getCompany())
                .build();
    }


}
