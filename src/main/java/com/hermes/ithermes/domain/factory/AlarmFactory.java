package com.hermes.ithermes.domain.factory;

import com.hermes.ithermes.domain.entity.Alarm;
import com.hermes.ithermes.domain.entity.Service;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.exception.EnumTypeFormatException;
import com.hermes.ithermes.domain.exception.WrongIdOrPasswordException;
import com.hermes.ithermes.domain.util.ActiveType;
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
import java.util.Optional;

@Builder
@Component
@RequiredArgsConstructor
public class AlarmFactory {
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;

    public Alarm parsePutSubscribeToAlarm(User user, Service service, ActiveType activeType, JobType jobType, String minYearOfExperience, String maxYearOfExperience) {
        if (service.getName().equals(ServiceType.SARAMIN) || service.getName().equals(ServiceType.WANTED)) {
            return insertJobData(user, service, activeType, jobType, minYearOfExperience, maxYearOfExperience);
        }
        return insertYoutubeOrNewsData(user, service, activeType);
    }

    private Alarm insertJobData(User user, Service service, ActiveType activeType, JobType jobType, String minYearOfExperience, String maxYearOfExperience) {
        Alarm alarm = Alarm.builder()
                .user(user)
                .service(service)
                .isActive(activeType)
                .job(jobType)
                .minYearOfExperience(parseStringToYearOfExperience(minYearOfExperience))
                .maxYearOfExperience(parseStringToYearOfExperience(maxYearOfExperience))
                .build();
        return alarm;
    }

    private Alarm insertYoutubeOrNewsData(User user, Service service, ActiveType activeType) {
        Alarm alarm = Alarm.builder()
                .user(user)
                .service(service)
                .isActive(activeType)
                .build();
        return alarm;
    }

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
            Optional<Alarm> alarmOptional = alarmRepository.findByServiceId(Long.valueOf(index + 1));
            alarmOptional.ifPresentOrElse(
                    v -> {
                        v.changeIsActive(activeType);
                        v.changeUpdateAt();
                        alarms.add(v);
                    }
                    , () -> {
                        var service = services.stream()
                                .filter(v -> v.getName().equals(serviceType))
                                .findFirst().orElseThrow(() -> new EnumTypeFormatException());

                        Alarm alarm = parsePutSubscribeToAlarm(user, service, activeType, jobType, minYearOfExperience, maxYearOfExperience);
                        alarms.add(alarm);
                    }
            );
            index++;
        }
        return alarms;
    }

    public User parseFindAlarmDtoToUser(AlarmFindAlarmRequestDto alarmFindAlarmRequestDto) {
        String loginId = alarmFindAlarmRequestDto.getId();
        return userRepository.findByLoginId(loginId).orElseThrow(() -> new WrongIdOrPasswordException());
    }

    public Integer parseStringToYearOfExperience(String yearOfExperience) {
        return yearOfExperience == null ? null : Integer.valueOf(yearOfExperience);
    }

    public JobType parseJobType(String job) {
        return job == null ? null : JobType.valueOf(job);
    }
}



