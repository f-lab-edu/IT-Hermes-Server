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

    public static boolean contentsTypeContains(String type){
        for(ContentsType c:ContentsType.values()){
            if(c.getName().equals(type)){
                return true;
            }
        }
        return false;
    }

}
