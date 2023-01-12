package com.hermes.ithermes.domain.util;

import lombok.Getter;

@Getter
public enum OrderType {

    RECENT("recent"),
    POPULAR("popular");

    private String name;

    OrderType(String name) {
        this.name = name;
    }
}
