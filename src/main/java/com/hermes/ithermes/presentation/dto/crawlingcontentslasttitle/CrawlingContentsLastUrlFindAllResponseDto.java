package com.hermes.ithermes.presentation.dto.crawlingcontentslasttitle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CrawlingContentsLastUrlFindAllResponseDto {
    List<CrawlingContentsLastUrlDto> crawlingContentsLastUrlDtoList;
}
