package com.hermes.ithermes.domain.util;

import lombok.Getter;

@Getter
public enum ContentsType {

    JOB("JOB"),
    NEWS("NEWS"),
    YOUTUBE("YOUTUBE"),
    YOUTUBEANDNEWS("YOUTUBEANDNEWS");

    private String name;

    ContentsType(String name) {
        this.name = name;
    }
}
