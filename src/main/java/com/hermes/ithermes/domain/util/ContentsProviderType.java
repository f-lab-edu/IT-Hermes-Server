package com.hermes.ithermes.domain.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum ContentsProviderType {
    SARAMIN("SARAMIN"),
    WANTED("WANTED"),
    CODING_WORLD("CODING_WORLD"),
    YOZM("YOZM"),
    NOMAD_CODERS("NOMAD_CODERS"),
    DREAM_CODING("DREAM_CODING");

    private String title;

    ContentsProviderType(String title) {
        this.title = title;
    }

    @JsonCreator
    public static ContentsProviderType fromValue(String contentsProvider) {
        return ContentsProviderType.valueOf(contentsProvider.toUpperCase());
    }

}