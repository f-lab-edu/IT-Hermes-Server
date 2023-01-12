package com.hermes.ithermes.domain.util;

import lombok.Getter;

@Getter
public enum OrderType {

    RECENT("RECENT"),
    POPULAR("POPULAR");

    private String name;

    OrderType(String name) {
        this.name = name;
    }

    public static boolean orderTypeContains(String type){
        for(OrderType o: OrderType.values()){
            if(o.getName().equals(type)){
                return true;
            }
        }
        return false;
    }

}
