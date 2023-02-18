package com.hermes.ithermes.domain.util;

import lombok.Getter;

@Getter
public enum RecommendKeywordType {

    RecommendKeyword1("머신러닝"),
    RecommendKeyword2("빅데이터"),
    RecommendKeyword3("보안"),
    RecommendKeyword4("오픈소스"),
    RecommendKeyword5("클라우드"),
    RecommendKeyword6("프레임워크");

    private String name;

    RecommendKeywordType(String name) {
        this.name = name;
    }

}
