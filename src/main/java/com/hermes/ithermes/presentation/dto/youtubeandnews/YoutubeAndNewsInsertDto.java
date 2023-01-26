package com.hermes.ithermes.presentation.dto.youtubeandnews;

import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class YoutubeAndNewsInsertDto {
    private CategoryType category;
    private ContentsProviderType contentsProvider;
    ArrayList<YoutubeAndNewsCrawlingDto> youtubeAndNewsCrawlingDtoList;
}
