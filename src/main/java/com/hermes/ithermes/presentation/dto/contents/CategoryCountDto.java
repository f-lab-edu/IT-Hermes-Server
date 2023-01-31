package com.hermes.ithermes.presentation.dto.contents;

import lombok.Getter;

@Getter
public class CategoryCountDto {

    private int youtubeCnt;
    private int jobCnt;
    private int newsCnt;

    public CategoryCountDto(int youtubeCnt, int jobCnt, int newsCnt) {
        this.youtubeCnt = youtubeCnt;
        this.jobCnt = jobCnt;
        this.newsCnt = newsCnt;
    }
}
