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
    DREAM_CODING("DREAM_CODING"),
    WHITESHIP("WHITESHIP"),
    FI("FI"),
    LINE_DEVELOP("LINE_DEVELOP"),
    DEVELOP_FOOT("DEVELOP_FOOT"),
    NULLNULL_DEVELOP("NULLNULL_DEVELOP"),
    DONGBINNA("DONGBINNA"),
    POPE("POPE"),
    WOOWA_COURSE("WOOWA_COURSE");

    private String title;

    ContentsProviderType(String title) {
        this.title = title;
    }

    @JsonCreator
    public static ContentsProviderType fromValue(String contentsProvider) {
        return ContentsProviderType.valueOf(contentsProvider.toUpperCase());
    }

}