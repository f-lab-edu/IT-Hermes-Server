package com.hermes.ithermes.presentation.dto.alarm;

import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class YoutubeAndNewsAlarmDto {

    private String title;

    private String description;

    private String image;

    private String url;

    private LocalDateTime contentsStartAt;

    private ContentsProviderType contentsProviderType;

    public static YoutubeAndNewsAlarmDto convertEntityToDto(YoutubeAndNews youtubeAndNews){
        return YoutubeAndNewsAlarmDto.builder()
                .title(youtubeAndNews.getTitle())
                .description(youtubeAndNews.getDescription())
                .image(youtubeAndNews.getImage())
                .url(youtubeAndNews.getUrl())
                .contentsStartAt(youtubeAndNews.getContentsStartAt())
                .build();
    }

}
