package com.hermes.ithermes.domain.factory;

import com.hermes.ithermes.domain.entity.Keyword;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.entity.UserKeywordRegistry;
import org.springframework.stereotype.Component;

@Component
public class UserKeywordRegistryFactory {

    public UserKeywordRegistry parseUserAndKeyword(User user, Keyword keyword) {
        UserKeywordRegistry userKeywordRegistry = UserKeywordRegistry.builder()
                .user(user)
                .keyword(keyword)
                .build();
        return userKeywordRegistry;
    }
}
