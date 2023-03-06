package com.hermes.ithermes.presentation.dto.contents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCountDto {
    private long youtubeCnt;
    private long jobCnt;
    private long newsCnt;
}
