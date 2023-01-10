package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Alarm;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.exception.EnumTypeFormatException;
import com.hermes.ithermes.domain.exception.WrongIdOrPasswordException;
import com.hermes.ithermes.domain.factory.AlarmFactory;
import com.hermes.ithermes.domain.util.ActiveType;
import com.hermes.ithermes.domain.util.JobType;
import com.hermes.ithermes.domain.util.ServiceType;
import com.hermes.ithermes.infrastructure.AlarmRepository;
import com.hermes.ithermes.infrastructure.ServiceRepository;
import com.hermes.ithermes.infrastructure.UserRepository;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.alarm.AlarmFindSubscribeRequestDto;
import com.hermes.ithermes.presentation.dto.alarm.AlarmFindSubscribeResponseDto;
import com.hermes.ithermes.presentation.dto.alarm.AlarmUpdateSubscribeRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class AlarmService {
    UserRepository userRepository;
    ServiceRepository serviceRepository;
    AlarmRepository alarmRepository;
    AlarmFactory alarmFactory;

    @Autowired
    public AlarmService(UserRepository userRepository, ServiceRepository serviceRepository, AlarmRepository alarmRepository, AlarmFactory alarmFactory) {
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
        this.alarmRepository = alarmRepository;
        this.alarmFactory = alarmFactory;
    }

    @Transactional
    public CommonResponseDto updateSubscribe(AlarmUpdateSubscribeRequestDto alarmUpdateSubScribeActiveRequestDto) {
        List<com.hermes.ithermes.domain.entity.Service> serviceList = serviceRepository.findAll();
        User user = userRepository.findByLoginId(alarmUpdateSubScribeActiveRequestDto.getId()).orElseThrow(() -> new WrongIdOrPasswordException());
        String startDateOfExperience = alarmUpdateSubScribeActiveRequestDto.getStartDateOfExperience();
        String endDateOfExperience = alarmUpdateSubScribeActiveRequestDto.getEndDateOfExperience();
        JobType jobType = JobType.parseJobType(alarmUpdateSubScribeActiveRequestDto.getJob());
        int index = 0;
        for (String active : alarmUpdateSubScribeActiveRequestDto.getKeywordList()) {
            ServiceType serviceType = ServiceType.parseCodeToServiceType(index);
            log.info("ServiceType = {}", serviceType.name());
            ActiveType activeType = ActiveType.parseActiveType(active);
            log.info("ActiveType = {}", activeType.name());
            log.info("ServiceTypeCode = {}", serviceType.getCode());
            Optional<Alarm> alarmOptional = alarmRepository.findByServiceId(Long.valueOf(serviceType.getCode() + 1));
            alarmOptional.ifPresentOrElse(
                    v -> {
                        v.changeIsActive(activeType);
                        v.changeUpdateAt();
                        alarmRepository.save(v);
                    }
                    , () -> {
                        com.hermes.ithermes.domain.entity.Service service = serviceList.stream().filter(v -> v.getName().equals(serviceType)).findFirst().orElseThrow(() -> new EnumTypeFormatException());
                        Alarm alarm = alarmFactory.parseUpdateSubscribeToAlarm(user, service, activeType, jobType, startDateOfExperience, endDateOfExperience);
                        alarmRepository.save(alarm);
                    }
            );
            index++;
        }
        return new CommonResponseDto();
    }

    public AlarmFindSubscribeResponseDto findSubscribe(AlarmFindSubscribeRequestDto alarmFindSubScribeRequestDto) {
        User user = userRepository.findByLoginId(alarmFindSubScribeRequestDto.getId()).orElseThrow(() -> new WrongIdOrPasswordException());
        List<Alarm> alarms = alarmRepository.findByUserId(user.getId()).orElseThrow(() -> new WrongIdOrPasswordException());
        List<String> activeTypeList = alarms.stream().map(v -> v.getIsActive().getTitle()).toList();

        Alarm alarmOptional = alarms.stream().filter(v -> Objects.nonNull(v.getJob()) || Objects.nonNull(v.getMinDate()) || Objects.nonNull(v.getMaxDate()))
                .findFirst().orElse(null);
        String job= Optional.ofNullable(alarmOptional.getJob().getTitle()).orElse(null);
        String startDateOfExperience = Optional.ofNullable(alarmOptional.getMinDate()).orElse(null);
        String endDateOfExperience = Optional.ofNullable(alarmOptional.getMaxDate()).orElse(null);
        return new AlarmFindSubscribeResponseDto(activeTypeList,job,startDateOfExperience,endDateOfExperience);
    }
}
