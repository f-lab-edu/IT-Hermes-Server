package com.hermes.ithermes.domain.util;

import com.hermes.ithermes.domain.exception.EnumTypeFormatException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum JobType {
    FRONT("front"),
    BACKEND("backend"),
    MOBILE("mobile");

    private String title;

    private JobType(String title) {
        this.title = title;
    }

    public static JobType parseJobType(String title) {
        return Arrays.stream(values()).filter(v -> v.getTitle().equals(title)).findFirst().orElseThrow(() -> new EnumTypeFormatException());
    }
}
