package com.hermes.ithermes.domain.util;

import com.hermes.ithermes.domain.entity.Subscribe;
import com.hermes.ithermes.domain.entity.ContentsProvider;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.exception.EnumTypeFormatException;
import com.hermes.ithermes.domain.factory.ParseSubscribeFunctionalFactory;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum CategoryType {
    JOB("JOB", Arrays.asList(ContentsProviderType.SARAMIN, ContentsProviderType.WANTED), (User user, ContentsProvider contentsProvider, ActiveType activeType,
                                                                                          JobType jobType, String minYearOfExperience, String maxYearOfExperience) -> {
        Subscribe subscribe = Subscribe.builder()
                .user(user)
                .contentsProvider(contentsProvider)
                .isActive(activeType)
                .job(parseStringToMaxYearOfExperience(jobType))
                .minYearOfExperience(parseStringToMinYearOfExperience(minYearOfExperience))
                .maxYearOfExperience(parseStringToMaxYearOfExperience(maxYearOfExperience))
                .build();
        return subscribe;
    }), NEWS("NEWS", Arrays.asList(ContentsProviderType.CODING_WORLD, ContentsProviderType.NAVER, ContentsProviderType.YOZM), (User user, ContentsProvider contentsProvider, ActiveType activeType,
                                                                                                                               JobType jobType, String minYearOfExperience, String maxYearOfExperience) -> {
        Subscribe subscribe = Subscribe.builder()
                .user(user)
                .contentsProvider(contentsProvider)
                .isActive(activeType)
                .build();
        return subscribe;

    }),
    YOUTUBE("YOUTUBE", Arrays.asList(ContentsProviderType.NOMAD_CODERS, ContentsProviderType.DREAM_CODING), (User user, ContentsProvider contentsProvider, ActiveType activeType,
                                                                                                             JobType jobType, String minYearOfExperience, String maxYearOfExperience) -> {
        Subscribe subscribe = Subscribe.builder()
                .user(user)
                .contentsProvider(contentsProvider)
                .isActive(activeType)
                .build();
        return subscribe;
    });

    private String title;
    private List<ContentsProviderType> contentsProviderTypes;
    // custom 함수형 인터페이스
    private ParseSubscribeFunctionalFactory parseSubscribe;

    CategoryType(String title, List<ContentsProviderType> contentsProviderTypes, ParseSubscribeFunctionalFactory parseSubscribe) {
        this.title = title;
        this.contentsProviderTypes = contentsProviderTypes;
        this.parseSubscribe = parseSubscribe;
    }

    private static Integer parseStringToMinYearOfExperience(String yearOfExperience) {
        return yearOfExperience == null ? 0 : Integer.valueOf(yearOfExperience);
    }

    private static Integer parseStringToMaxYearOfExperience(String yearOfExperience) {
        return yearOfExperience == null ? 30 : Integer.valueOf(yearOfExperience);
    }

    private static JobType parseStringToMaxYearOfExperience(JobType jobType) {
        return jobType == null ? JobType.ENTIRE : jobType;
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
}
