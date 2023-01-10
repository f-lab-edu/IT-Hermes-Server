package com.hermes.ithermes.domain.factory;

import com.hermes.ithermes.domain.entity.Keyword;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Builder
@Component
public class KeywordFactory {
    public Keyword parseKeywordNameToKeyword(String keywordName) {
        Keyword keyword = Keyword.builder().name(keywordName).build();
        keyword.initDefaultValue();
        return keyword;
    }
}
