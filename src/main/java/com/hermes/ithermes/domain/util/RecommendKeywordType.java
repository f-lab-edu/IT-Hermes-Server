package com.hermes.ithermes.domain.util;

import lombok.Getter;

@Getter
public enum RecommendKeywordType {

    MachineLearning("머신러닝"),
    BigData("빅데이터"),
    Security("보안"),
    OpenSource("오픈소스"),
    Cloud("클라우드"),
    Framework("프레임워크");

    private String name;

    RecommendKeywordType(String name) {
        this.name = name;
    }

}
