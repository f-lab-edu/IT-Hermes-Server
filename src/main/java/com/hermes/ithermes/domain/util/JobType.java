package com.hermes.ithermes.domain.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum JobType {
    FRONT("FRONT"),
    BACKEND("BACKEND"),
    MOBILE("MOBILE");

    private String title;

    JobType(String title) {
        this.title = title;
    }

    @JsonCreator
    public static JobType fromValue(String job) {
        return JobType.valueOf(job.toUpperCase());
    }
}
