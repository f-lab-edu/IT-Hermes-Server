package com.hermes.ithermes.domain.util;

import lombok.Getter;

@Getter
public enum ContentsType {

    YOUTUBE("YOUTUBE"),
    NEWS("NEWS"),
    JOB("JOB"),
    YOUTUBE_AND_NEWS("YOUTUBE_AND_NEWS");

    private String name;

    ContentsType(String name) {
        this.name = name;
    }

    public static boolean isContainContentsType(String type){
        for(ContentsType c: ContentsType.values()){
            if(c.equals(type)){
                return true;
            }
        }
        return false;
    }

}
