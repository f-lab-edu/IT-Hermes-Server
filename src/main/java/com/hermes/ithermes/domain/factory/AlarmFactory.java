package com.hermes.ithermes.domain.factory;

import com.hermes.ithermes.domain.entity.Alarm;
import com.hermes.ithermes.domain.entity.Service;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.exception.WrongIdOrPasswordException;
import com.hermes.ithermes.domain.util.ActiveType;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ServiceType;
import com.hermes.ithermes.infrastructure.AlarmRepository;
import com.hermes.ithermes.presentation.dto.alarm.AlarmFindAlarmRequestDto;
import com.hermes.ithermes.presentation.dto.alarm.AlarmPutAlarmRequestDto;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Builder
@Component
@RequiredArgsConstructor
public class AlarmFactory {
    private final AlarmRepository alarmRepository;
    private final ServiceFactory serviceFactory;
    private final UserFactory userFactory;

    public List<Alarm> parsePutAlarmDtoToAlarms(AlarmPutAlarmRequestDto alarmPutAlarmRequestDto) {
        List<Alarm> alarms = new ArrayList<>();
        List<Service> services = serviceFactory.findAllService();
        String loginId = alarmPutAlarmRequestDto.getId();
        User user = userFactory.findLoginId(loginId).orElseThrow(() -> new WrongIdOrPasswordException());
        String minYearOfExperience = alarmPutAlarmRequestDto.getMinYearOfExperience();
        String maxYearOfExperience = alarmPutAlarmRequestDto.getMaxYearOfExperience();
        String jobType = alarmPutAlarmRequestDto.getJob();

        int index = 0;
        for (String active : alarmPutAlarmRequestDto.getKeywordList()) {
            ServiceType serviceType = ServiceType.values()[index];
            ActiveType activeType = ActiveType.valueOf(active);
            Service service = services.get(index);
            Long serviceId = service.getId();
            CategoryType categoryType = CategoryType.findByServiceType(serviceType);
            Optional<Alarm> alarmOptional = findByServiceId(serviceId);

            Alarm alarm = categoryType.getParseAlarm().parseAlarm(user, service, activeType, jobType, minYearOfExperience, maxYearOfExperience);

            alarmOptional.ifPresent(v -> {
                alarm.changeUpdateAt();
            });
            alarms.add(alarm);
            index++;
        }
        return alarms;
    }

    public List<Alarm> parseFindAlarmDtoToAlarms(AlarmFindAlarmRequestDto alarmFindAlarmRequestDto) {
        String loginId = alarmFindAlarmRequestDto.getId();
        User user = userFactory.findLoginId(loginId).orElseThrow(() -> new WrongIdOrPasswordException());
        Long userId = user.getId();
        List<Alarm> alarms = findByUserId(userId);
        return alarms;
    }

    public List<String> findActiveServiceType(List<Alarm> alarms) {
        return alarms.stream().map(v -> v.getIsActive().getTitle()).toList();
    }

    public Alarm findJobCategoryData(List<Alarm> alarms) {
        return alarms.stream()
                .filter(v -> Objects.nonNull(v.getJob()) || Objects.nonNull(v.getMinYearOfExperience()) || Objects.nonNull(v.getMaxYearOfExperience()))
                .findAny().orElse(null);
    }

    public Optional<Alarm> findByServiceId(Long serviceId) {
        return alarmRepository.findByServiceId(serviceId);
    }

    public List<Alarm> findByUserId(Long userId) {
        return alarmRepository.findByUserId(userId).orElseThrow(() -> new WrongIdOrPasswordException());
    }
}



