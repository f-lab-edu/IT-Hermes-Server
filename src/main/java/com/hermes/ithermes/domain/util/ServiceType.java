package com.hermes.ithermes.domain.util;

import lombok.Getter;

@Getter
public enum ServiceType {
    SARAMIN("SARAMIN"),
    WANTED("WANTED"),
    CODING_WORLD("CONINGWORLD"),
    NAVER("NAVER"),
    YOZM("YOZM"),
    NOMAD_CODERS("NOMADCODERS"),
    DREAM_CODING("DREAMCODING");

    private String title;

    ServiceType(String title) {
        this.title = title;
    }
}