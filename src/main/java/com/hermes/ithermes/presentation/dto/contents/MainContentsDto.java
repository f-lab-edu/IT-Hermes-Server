package com.hermes.ithermes.presentation.dto.contents;

import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MainContentsDto {

    public String title;

    public String image;

    public String url;

    public String category;

    public String service;

    public String contentsDate;

    public static MainContentsDto ContentsEntityToDto(YoutubeAndNews youtubeAndNews){
        return MainContentsDto.builder()
                .title(youtubeAndNews.getTitle())
                .image(youtubeAndNews.getImage())
                .url(youtubeAndNews.getUrl())
                .category(youtubeAndNews.getService().getCategory())
                .service(youtubeAndNews.getService().getName())
                .contentsDate(youtubeAndNews.getContentsDate())
                .build();
    }

}
