package com.hermes.ithermes.domain.factory;

import com.hermes.ithermes.domain.entity.Alarm;
import com.hermes.ithermes.domain.entity.Service;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.exception.WrongIdOrPasswordException;
import com.hermes.ithermes.domain.util.ActiveType;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.JobType;
import com.hermes.ithermes.domain.util.ServiceType;
import com.hermes.ithermes.infrastructure.AlarmRepository;
import com.hermes.ithermes.infrastructure.ServiceRepository;
import com.hermes.ithermes.infrastructure.UserRepository;
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
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;

    public List<Alarm> parsePutAlarmDtoToAlarms(AlarmPutAlarmRequestDto alarmPutAlarmRequestDto) {
        List<Service> services = serviceRepository.findAll();
        List<Alarm> alarms = new ArrayList<>();
        User user = userRepository.findByLoginId(alarmPutAlarmRequestDto.getId()).orElseThrow(() -> new WrongIdOrPasswordException());
        String minYearOfExperience = alarmPutAlarmRequestDto.getMinYearOfExperience();
        String maxYearOfExperience = alarmPutAlarmRequestDto.getMaxYearOfExperience();
        JobType jobType = parseJobType(alarmPutAlarmRequestDto.getJob());

        int index = 0;
        for (String active : alarmPutAlarmRequestDto.getKeywordList()) {
            ServiceType serviceType = ServiceType.values()[index];
            ActiveType activeType = ActiveType.valueOf(active);
            Service service = services.get(index);
            Long serviceId = service.getId();
            CategoryType categoryType = CategoryType.findByServiceType(serviceType);
            Optional<Alarm> alarmOptional = alarmRepository.findByServiceId(serviceId);

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
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new WrongIdOrPasswordException());
        Long userId = user.getId();
        List<Alarm> alarms = alarmRepository.findByUserId(userId).orElseThrow(() -> new WrongIdOrPasswordException());
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

    public JobType parseJobType(String job) {
        return job == null ? null : JobType.valueOf(job);
    }
}



