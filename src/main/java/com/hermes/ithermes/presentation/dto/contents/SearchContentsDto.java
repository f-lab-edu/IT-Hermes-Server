package com.hermes.ithermes.presentation.dto.contents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchContentsDto {

    public int count;

    public List<ContentsDtoInterface> searchContentsList;

}
