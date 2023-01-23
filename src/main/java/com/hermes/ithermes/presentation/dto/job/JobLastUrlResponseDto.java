package com.hermes.ithermes.presentation.dto.job;

import com.hermes.ithermes.domain.util.ContentsProviderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobLastUrlResponseDto {
    private ContentsProviderType contentsProvider;
    private String lastUrl;
}
