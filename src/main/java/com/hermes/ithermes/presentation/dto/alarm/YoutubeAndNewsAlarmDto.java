package com.hermes.ithermes.presentation.dto.alarm;

import com.hermes.ithermes.domain.util.ContentsProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class YoutubeAndNewsAlarmDto {

    private String title;

    private String description;

    private String image;

    private String url;

    private LocalDateTime contentsStartAt;

    private ContentsProviderType contentsProviderType;

}
