package com.hermes.ithermes.domain.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.hermes.ithermes.domain.entity.Subscribe;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.exception.EnumTypeFormatException;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum CategoryType {
    JOB("JOB", Arrays.asList(ContentsProviderType.SARAMIN, ContentsProviderType.WANTED)),
    NEWS("NEWS", Arrays.asList(ContentsProviderType.CODING_WORLD, ContentsProviderType.YOZM)),
    YOUTUBE("YOUTUBE", Arrays.asList(ContentsProviderType.NOMAD_CODERS, ContentsProviderType.DREAM_CODING)),
    YOUTUBE_AND_NEWS("YOUTUBE_AND_NEWS",Arrays.asList(ContentsProviderType.NOMAD_CODERS,ContentsProviderType.DREAM_CODING,
            ContentsProviderType.CODING_WORLD,ContentsProviderType.YOZM));

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

    @JsonCreator
    public static CategoryType fromValue(String category) {
        return CategoryType.valueOf(category.toUpperCase());
    }

    public static boolean isContainCategoryType(String title){
        for(CategoryType c: CategoryType.values()){
            if(c.getTitle().equals(title)){
                return true;
            }
        }
        return false;
    }
}
