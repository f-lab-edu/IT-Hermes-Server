package com.hermes.ithermes.presentation.dto.alarm;

import com.hermes.ithermes.domain.util.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class YoutubeAndNewsAlarmDto {

    private String title;

    private CategoryType categoryType;

    private String name;

    private String description;

    private String image;

    private String url;

}
