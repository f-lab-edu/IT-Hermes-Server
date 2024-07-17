package com.hermes.ithermes.domain.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum JobType {
    FRONT("FRONT", Arrays.asList("UIUX","React","Vue","HTML","CSS")),
    BACKEND("BACKEND",Arrays.asList("데이터베이스","API")),
    MOBILE("MOBILE",Arrays.asList("iOS","안드로이드"));

    private String title;
    private List<String> keywords;

    JobType(String title, List<String> keywords) {
        this.title = title;
        this.keywords = keywords;
    }

    @JsonCreator
    public static JobType fromValue(String job) {
        return JobType.valueOf(job.toUpperCase());
    }
}
