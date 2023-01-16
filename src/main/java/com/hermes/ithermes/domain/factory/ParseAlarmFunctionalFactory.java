package com.hermes.ithermes.domain.factory;

import com.hermes.ithermes.domain.entity.Alarm;
import com.hermes.ithermes.domain.entity.Service;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.util.ActiveType;
import com.hermes.ithermes.domain.util.JobType;

@FunctionalInterface
public interface ParseAlarmFunctionalFactory {
    Alarm parseAlarm(User user, Service service, ActiveType activeType, JobType jobType, String minYearOfExperience, String maxYearOfExperience);
}
