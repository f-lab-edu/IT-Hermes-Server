package com.hermes.ithermes.domain.functional;

import com.hermes.ithermes.domain.entity.Alarm;
import com.hermes.ithermes.domain.entity.Service;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.util.ActiveType;

@FunctionalInterface
public interface ParseAlarmFunctional {
    Alarm parseAlarm(User user, Service service, ActiveType activeType, String jobType, String minYearOfExperience, String maxYearOfExperience);
}
