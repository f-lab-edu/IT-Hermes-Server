package com.hermes.ithermes.domain.util;

import lombok.Getter;

@Getter
public enum OrderType {

    ID("ID","createdAt"),
    RECENT("RECENT","contentsStartAt"),
    POPULAR("POPULAR","viewCount");

    private String name;
    private String orderQuery;

    OrderType(String name, String orderQuery) {
        this.name = name;
        this.orderQuery = orderQuery;
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
