package com.hermes.ithermes.presentation.dto.contents;

import com.hermes.ithermes.domain.entity.ContentsEntityInterface;

public interface ContentsDtoInterface {

  ContentsDtoInterface convertEntityToDto(ContentsEntityInterface contentsEntityInterface);

}
