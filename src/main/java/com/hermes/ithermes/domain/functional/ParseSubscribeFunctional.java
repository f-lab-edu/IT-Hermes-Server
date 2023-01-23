package com.hermes.ithermes.domain.functional;

import com.hermes.ithermes.domain.entity.ContentsProvider;
import com.hermes.ithermes.domain.entity.Subscribe;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.util.ActiveType;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsProviderType;

@FunctionalInterface
public interface ParseSubscribeFunctional {
    Subscribe parseSubscribe(User user, ContentsProvider contentsProvider, CategoryType categoryType, ContentsProviderType contentsProviderType, ActiveType activeType);
}
