package com.hermes.ithermes.presentation.dto.urlrecord;

import com.hermes.ithermes.domain.util.ContentsProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UrlRecordPutViewCountRequestDto {
    private String url;
    private ContentsProviderType contentsProviderType;
}
