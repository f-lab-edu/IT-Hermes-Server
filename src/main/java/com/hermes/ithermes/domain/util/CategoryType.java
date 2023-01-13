package com.hermes.ithermes.domain.util;

import lombok.Getter;

@Getter
public enum CategoryType {

    JOB("JOB"),
    NEWS("NEWS"),
    YOUTUBE("YOUTUBE"),
    YOUTUBE_AND_NEWS("YOUTUBE_AND_NEWS");

    private String name;

    CategoryType(String name) {
        this.name = name;
    }

    public static boolean categoryTypeContains(String type){
        for(CategoryType c: CategoryType.values()){
            if(c.getName().equals(type)){
                return true;
            }
        }
        return false;
    }

}
