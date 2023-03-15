package com.hermes.ithermes.presentation.security;

import lombok.Getter;

@Getter
public enum SecurityErrorCode {
    WRONG_TYPE_TOKEN("WRONG_TYPE_TOKEN"),
    EXPIRED_TOKEN("EXPIRED_TOKEN");

    private String code;

    SecurityErrorCode(String code) {
        this.code = code;
    }
}
