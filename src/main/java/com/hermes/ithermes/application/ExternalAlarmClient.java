package com.hermes.ithermes.application;

import com.hermes.ithermes.presentation.dto.alarm.AlarmDtoInterface;
import com.hermes.ithermes.presentation.dto.alarm.JobAlarmDto;
import com.hermes.ithermes.presentation.dto.alarm.YoutubeAndNewsAlarmDto;

import java.util.List;

public interface ExternalAlarmClient {

    void sendContentsMessage(List<AlarmDtoInterface> youtubeAlarmDtoList, String telegramId);
    void sendJobMessage(List<JobAlarmDto> jobAlarmDtoList, String telegramId);

}
