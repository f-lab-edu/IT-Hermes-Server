package com.hermes.ithermes.domain.factory;

import com.hermes.ithermes.domain.entity.Alarm;
import com.hermes.ithermes.domain.entity.Service;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.util.ActiveType;
import com.hermes.ithermes.domain.util.JobType;
import com.hermes.ithermes.domain.util.ServiceType;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Builder
@Component
public class AlarmFactory {
    public Alarm parseUpdateSubscribeToAlarm(User user, Service service, ActiveType activeType, JobType jobType, String startDateOfExperience, String endDateOfExperience) {
        if (service.getName().equals(ServiceType.SARAMIN) || service.getName().equals(ServiceType.WANTED)) {
            return insertJobData(user, service, activeType, jobType, startDateOfExperience, endDateOfExperience);
        }
        return insertYoutubeOrNewsData(user, service, activeType);
    }

    private Alarm insertJobData(User user, Service service, ActiveType activeType, JobType jobType, String startDateOfExperience, String endDateOfExperience) {
        Alarm alarm = Alarm.builder()
                .user(user)
                .service(service)
                .isActive(activeType)
                .job(jobType)
                .minDate(startDateOfExperience)
                .maxDate(endDateOfExperience)
                .build();
        alarm.initDefaultValue();
        return alarm;
    }

    private Alarm insertYoutubeOrNewsData(User user, Service service, ActiveType activeType) {
        Alarm alarm = Alarm.builder()
                .user(user)
                .service(service)
                .isActive(activeType)
                .build();
        alarm.initDefaultValue();
        return alarm;
    }
}



