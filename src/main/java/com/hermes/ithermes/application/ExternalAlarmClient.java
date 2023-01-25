package com.hermes.ithermes.application;

import com.hermes.ithermes.presentation.dto.alarm.JobAlarmDto;
import com.hermes.ithermes.presentation.dto.alarm.YoutubeAndNewsAlarmDto;

import java.util.List;

public interface ExternalAlarmClient {

    void sendYoutubeAndNewsMessage(List<YoutubeAndNewsAlarmDto> youtubeAlarmDtoList, long userIdx);
    void sendJobMessage(List<JobAlarmDto> jobAlarmDtoList, long userIdx);

}
