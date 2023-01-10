package com.hermes.ithermes.domain.factory;

import com.hermes.ithermes.domain.entity.Service;
import com.hermes.ithermes.presentation.dto.alarm.AlarmUpdateSubscribeRequestDto;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Builder
@Component
public class ServiceFactory {
    public Service parseAlarmUpdateSubscribeRequestDtoToService(AlarmUpdateSubscribeRequestDto alarmUpdateSubScribeRequestDto) {
        Service service = Service.builder()
                .build();
        service.initDefaultValue();
        return service;
    }
}
