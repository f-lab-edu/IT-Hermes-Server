package com.hermes.ithermes.presentation.dto.contents;

import com.hermes.ithermes.domain.entity.CrawlingContents;

public interface ContentsDtoInterface {

  ContentsDtoInterface convertEntityToDto(CrawlingContents crawlingContents);

}
