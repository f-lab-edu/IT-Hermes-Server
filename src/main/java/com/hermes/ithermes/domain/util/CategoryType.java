package com.hermes.ithermes.domain.util;

import com.hermes.ithermes.domain.entity.Subscribe;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.exception.EnumTypeFormatException;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum CategoryType {
    JOB("JOB", Arrays.asList(ContentsProviderType.SARAMIN, ContentsProviderType.WANTED)),
    NEWS("NEWS", Arrays.asList(ContentsProviderType.CODING_WORLD, ContentsProviderType.NAVER, ContentsProviderType.YOZM)),
    YOUTUBE("YOUTUBE", Arrays.asList(ContentsProviderType.NOMAD_CODERS, ContentsProviderType.DREAM_CODING));

    private String title;
    private List<ContentsProviderType> contentsProviderTypes;

    CategoryType(String title, List<ContentsProviderType> contentsProviderTypes) {
        this.title = title;
        this.contentsProviderTypes = contentsProviderTypes;
    }

    public static CategoryType findByContentsProviderType(ContentsProviderType contentsProviderType) {
        return Arrays.stream(CategoryType.values())
                .filter(categoryType -> categoryType.matchContentsProviderType(contentsProviderType))
                .findAny()
                .orElseThrow(() -> new EnumTypeFormatException());
    }

    public boolean matchContentsProviderType(ContentsProviderType contentsProviderType) {
        return contentsProviderTypes.stream()
                .anyMatch(contentsProviderTypeList -> contentsProviderTypeList.equals(contentsProviderType));
    }

    public static Subscribe parseSubscribe(User user, CategoryType categoryType, ContentsProviderType contentsProviderType, ActiveType activeType) {
        return Subscribe.builder()
                .user(user)
                .category(categoryType)
                .contentsProvider(contentsProviderType)
                .isActive(activeType)
                .build();
    }
}
