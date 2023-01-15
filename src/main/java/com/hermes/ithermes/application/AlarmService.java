package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Alarm;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.exception.WrongIdOrPasswordException;
import com.hermes.ithermes.domain.factory.AlarmFactory;
import com.hermes.ithermes.infrastructure.AlarmRepository;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.alarm.AlarmFindAlarmRequestDto;
import com.hermes.ithermes.presentation.dto.alarm.AlarmFindAlarmResponseDto;
import com.hermes.ithermes.presentation.dto.alarm.AlarmPutAlarmRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final AlarmFactory alarmFactory;

    @Transactional
    public CommonResponseDto putAlarm(AlarmPutAlarmRequestDto alarmPutAlarmRequestDto) {
        List<Alarm> alarms = alarmFactory.parsePutAlarmDtoToAlarms(alarmPutAlarmRequestDto);
        alarms.stream().forEach(v -> alarmRepository.save(v));
        return new CommonResponseDto();
    }

    public AlarmFindAlarmResponseDto findAlarm(AlarmFindAlarmRequestDto alarmFindSubScribeRequestDto) {
        User user = alarmFactory.parseFindAlarmDtoToUser(alarmFindSubScribeRequestDto);
        Long userId = user.getId();
        List<Alarm> alarms = alarmRepository.findByUserId(userId).orElseThrow(() -> new WrongIdOrPasswordException());

        List<String> activeTypes = alarms.stream()
                .map(v -> v.getIsActive().getTitle()).toList();

        Alarm alarmOptional = alarms.stream()
                .filter(v -> Objects.nonNull(v.getJob()) || Objects.nonNull(v.getMinYearOfExperience()) || Objects.nonNull(v.getMaxYearOfExperience()))
                .findFirst().orElse(null);

        String job = Optional.ofNullable(alarmOptional.getJob().getTitle()).orElse(null);
        String startDateOfExperience = Optional.ofNullable(String.valueOf(alarmOptional.getMinYearOfExperience())).orElse(null);
        String endDateOfExperience = Optional.ofNullable(String.valueOf(alarmOptional.getMaxYearOfExperience())).orElse(null);
        return new AlarmFindAlarmResponseDto(activeTypes, job, startDateOfExperience, endDateOfExperience);
    }
}
