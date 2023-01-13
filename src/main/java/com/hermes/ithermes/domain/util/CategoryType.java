package com.hermes.ithermes.domain.util;

import lombok.Getter;

@Getter
public enum CategoryType {
    JOB("JOB"),
    NEWS("NEWS"),
    YOUTUBE("YOUTUBE");

    private String title;

    CategoryType(String title) {
        this.title = title;
    }
}
