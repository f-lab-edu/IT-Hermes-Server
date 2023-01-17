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

    public static boolean isContainContentsType(String name){
        for(ContentsType c: ContentsType.values()){
            if(c.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

}
