package com.hermes.ithermes.presentation.dto.alarm;

import com.hermes.ithermes.domain.util.CategoryType;
import lombok.Getter;

@Getter
public class YoutubeAndNewsAlarmDto {

    private CategoryType categoryType;

    private String name;

    private String description;

    private String image;

    private String title;

    private String url;

}
