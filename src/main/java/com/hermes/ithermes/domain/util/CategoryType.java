package com.hermes.ithermes.domain.util;

import com.hermes.ithermes.domain.entity.Alarm;
import com.hermes.ithermes.domain.entity.Service;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.exception.EnumTypeFormatException;
import com.hermes.ithermes.domain.functional.ParseAlarmFunctional;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum CategoryType {
    JOB("JOB", Arrays.asList(ServiceType.SARAMIN, ServiceType.WANTED),
            (User user, Service service, ActiveType activeType, String jobType, String minYearOfExperience, String maxYearOfExperience) -> {
        Alarm alarm = Alarm.builder()
                .user(user)
                .service(service)
                .isActive(activeType)
                .minYearOfExperience(parseToMinYearOfExperience(minYearOfExperience))
                .maxYearOfExperience(parseToMaxYearOfExperience(maxYearOfExperience))
                .job(parseToJobType(jobType))
                .build();
        return alarm;
    }), NEWS("NEWS", Arrays.asList(ServiceType.CODING_WORLD, ServiceType.NAVER, ServiceType.YOZM),
            (User user, Service service, ActiveType activeType, String jobType, String minYearOfExperience, String maxYearOfExperience) -> {
        Alarm alarm = Alarm.builder()
                .user(user)
                .service(service)
                .isActive(activeType)
                .build();
        return alarm;

    }),
    YOUTUBE("YOUTUBE", Arrays.asList(ServiceType.NOMAD_CODERS, ServiceType.DREAM_CODING),
            (User user, Service service, ActiveType activeType, String jobType, String minYearOfExperience, String maxYearOfExperience) -> {
        Alarm alarm = Alarm.builder()
                .user(user)
                .service(service)
                .isActive(activeType)
                .build();
        return alarm;
    });

    private String title;
    private List<ServiceType> serviceTypes;
    private ParseAlarmFunctional parseAlarm;

    CategoryType(String title, List<ServiceType> serviceTypes, ParseAlarmFunctional parseAlarm) {
        this.title = title;
        this.serviceTypes = serviceTypes;
        this.parseAlarm = parseAlarm;
    }

    private static Integer parseToMinYearOfExperience(String yearOfExperience) {
        return yearOfExperience == null ? 0 : Integer.valueOf(yearOfExperience);
    }

    private static Integer parseToMaxYearOfExperience(String yearOfExperience) {
        return yearOfExperience == null ? 30 : Integer.valueOf(yearOfExperience);
    }

    private static JobType parseToJobType(String jobType) {
        return jobType == null ? JobType.ENTIRE : JobType.valueOf(jobType);
    }

    public static CategoryType findByServiceType(ServiceType serviceType) {
        return Arrays.stream(CategoryType.values())
                .filter(categoryType -> categoryType.matchServiceType(serviceType))
                .findAny()
                .orElseThrow(() -> new EnumTypeFormatException());
    }

    public boolean matchServiceType(ServiceType serviceType) {
        return serviceTypes.stream()
                .anyMatch(serviceTypeList -> serviceTypeList.equals(serviceType));
    }
}
