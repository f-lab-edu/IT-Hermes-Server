package com.hermes.ithermes.domain.util;

import lombok.Getter;

@Getter
public enum OrderType {

    ID("ID"),
    RECENT("RECENT"),
    POPULAR("POPULAR");

    private String name;

    OrderType(String name) {
        this.name = name;
    }

    public static boolean isContainOrderType(String type){
        for(OrderType o: OrderType.values()){
            if(o.getName().equals(type)){
                return true;
            }
        }
        return false;
    }

}
