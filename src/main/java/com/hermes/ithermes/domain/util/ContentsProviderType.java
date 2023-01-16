package com.hermes.ithermes.domain.util;

import lombok.Getter;

@Getter
public enum ContentsProviderType {
    SARAMIN("SARAMIN"),
    WANTED("WANTED"),
    CODING_WORLD("CONING_WORLD"),
    NAVER("NAVER"),
    YOZM("YOZM"),
    NOMAD_CODERS("NOMAD_CODERS"),
    DREAM_CODING("DREAM_CODING");

    private String title;

    ContentsProviderType(String title) {
        this.title = title;
    }
}