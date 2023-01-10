package com.hermes.ithermes.domain.util;

import com.hermes.ithermes.domain.exception.EnumTypeFormatException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ServiceType {
    SARAMIN("saramin", 0),
    WANTED("wanted", 1),
    CODING_WORLD("codingworld", 2),
    NAVER("naver", 3),
    YOZM("yozm", 4),
    NOMAD_CODERS("nomadcoders", 5),
    DREAM_CODING("dreamcoding", 6);

//    private static String[] serviceList
//            = {"saramin", "wanted", "codingworld", "naver"
//            , "yozm", "nomadcoders", "dreamcoding"};


    private String title;
    private int code;

    private ServiceType(String title, int code) {
        this.title = title;
        this.code = code;
    }

    public static ServiceType parseCodeToServiceType(int code) {
        return Arrays.stream(values()).filter(v -> v.getCode() == code).findFirst().orElseThrow(() -> new EnumTypeFormatException());
    }

    public static ServiceType parseTitleToServiceType(String title) {
        return Arrays.stream(values()).filter(v -> v.getTitle().equals(title)).findFirst().orElseThrow(() -> new EnumTypeFormatException());
    }
}