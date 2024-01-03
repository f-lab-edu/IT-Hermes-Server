package com.hermes.ithermes.presentation.dto.crawlingcontentslasttitle;

import com.hermes.ithermes.domain.entity.CrawlingContentsLastUrl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CrawlingContentsLastUrlFindAllResponseDto {
    List<CrawlingContentsLastUrl> crawlingContentsLastUrlDtoList;
}
