package com.hermes.ithermes.domain.util;

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
}
