package com.hermes.ithermes.domain.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum ElasticSearchType {
    READY("READY"),
    DONE("DONE");

    private String text;

    ElasticSearchType(String text) {
        this.text = text;
    }

    @JsonCreator
    public static ElasticSearchType fromValue(String text) {
        return ElasticSearchType.valueOf(text);
    }
}
