package com.hermes.ithermes.domain.util;

import lombok.Getter;

@Getter
public enum ActiveType {
    ACTIVE("ACTIVE"),
    NOT_ACTIVE("NOT_ACTIVE");

    private String title;

    ActiveType(String title) {
        this.title = title;
    }
}
