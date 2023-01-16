package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Alarm;
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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
        List<Alarm> alarms = alarmFactory.parseFindAlarmDtoToAlarms(alarmFindSubScribeRequestDto);
        List<String> serviceTypes = alarmFactory.findActiveServiceType(alarms);
        Alarm jobCategoryData = alarmFactory.findJobCategoryData(alarms);

        String job = String.valueOf(jobCategoryData.getJob().getTitle());
        String startDateOfExperience = String.valueOf(jobCategoryData.getMinYearOfExperience());
        String endDateOfExperience = String.valueOf(jobCategoryData.getMaxYearOfExperience());

        return new AlarmFindAlarmResponseDto(serviceTypes, job, startDateOfExperience, endDateOfExperience);
    }
}
