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
public class YoutubeAndNewsAlarmDto implements AlarmDtoInterface{

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

    @Override
    public String title() {
        return title;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public String image() {
        return image;
    }

    @Override
    public String url() {
        return url;
    }

    @Override
    public LocalDateTime contentsStartAt() {
        return contentsStartAt;
    }

    @Override
    public ContentsProviderType contentsProvider() {
        return contentsProviderType;
    }
}
