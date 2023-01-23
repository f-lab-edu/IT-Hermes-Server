package com.hermes.ithermes.presentation.dto.youtubeandnews;


import com.hermes.ithermes.domain.util.ContentsProviderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class YoutubeAndNewsLastUrlResponseDto {
    private ContentsProviderType contentsProvider;
    private String lastUrl;
}
