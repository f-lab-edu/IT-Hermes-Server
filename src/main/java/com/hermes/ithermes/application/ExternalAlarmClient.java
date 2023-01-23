package com.hermes.ithermes.application;

import com.hermes.ithermes.presentation.dto.alarm.JobAlarmDto;
import com.hermes.ithermes.presentation.dto.alarm.YoutubeAndNewsAlarmDto;

import java.util.List;

public interface ExternalAlarmClient {

    void sendYoutubeMessage(List<YoutubeAndNewsAlarmDto> youtubeAlarmDtoList, long userIdx);
    void sendNewsMessage(List<YoutubeAndNewsAlarmDto> newsAlarmDtoList, long userIdx);
    void sendJobMessage(List<JobAlarmDto> jobAlarmDtoList, long userIdx);

}
