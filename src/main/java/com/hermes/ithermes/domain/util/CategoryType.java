package com.hermes.ithermes.domain.util;

import com.hermes.ithermes.domain.exception.EnumTypeFormatException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CategoryType {
    JOB("job"),
    NEWS("category"),
    YOUTUBE("youtube");

    private String title;

    private CategoryType(String title) {
        this.title = title;
    }

    public static CategoryType parseCategoryType(String title) {
        return Arrays.stream(values()).filter(v -> v.getTitle().equals(title)).findFirst().orElseThrow(() -> new EnumTypeFormatException());
    }
}
