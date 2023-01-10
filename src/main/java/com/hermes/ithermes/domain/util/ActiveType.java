package com.hermes.ithermes.domain.util;

import com.hermes.ithermes.domain.exception.EnumTypeFormatException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ActiveType {
    ACTIVE("1"),
    NOT_ACTIVE("0");

    private String title;

    private ActiveType(String title) {
        this.title = title;
    }

    public static ActiveType parseActiveType(String title) {
        return Arrays.stream(values()).filter(v -> v.getTitle().equals(title)).findFirst().orElseThrow(() -> new EnumTypeFormatException());
    }
}
