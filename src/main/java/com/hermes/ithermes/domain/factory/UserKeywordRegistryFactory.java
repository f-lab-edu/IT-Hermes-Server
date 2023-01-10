package com.hermes.ithermes.domain.factory;

import com.hermes.ithermes.domain.entity.Keyword;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.entity.UserKeywordRegistry;
import org.springframework.stereotype.Component;

@Component
public class UserKeywordRegistryFactory {

    public UserKeywordRegistry parseUserAndKeyword(User user, Keyword keyword) {
        keyword.initDefaultValue();
        UserKeywordRegistry userKeywordRegistry = UserKeywordRegistry.builder().build();
        userKeywordRegistry.setUser(user);
        userKeywordRegistry.setKeyword(keyword);
        userKeywordRegistry.initDefaultValue();
        return userKeywordRegistry;
    }
}
