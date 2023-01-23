package com.hermes.ithermes.presentation.dto.youtubeandnews;

import com.hermes.ithermes.domain.util.ContentsProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class YoutubeAndNewsLastUrlRequestDto {
    private ContentsProviderType contentsProvider;
}
