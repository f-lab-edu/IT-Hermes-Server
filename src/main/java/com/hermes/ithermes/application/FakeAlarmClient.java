package com.hermes.ithermes.application;

import com.hermes.ithermes.presentation.dto.alarm.JobAlarmDto;
import com.hermes.ithermes.presentation.dto.alarm.YoutubeAndNewsAlarmDto;

import java.util.List;

//@Component
public class FakeAlarmClient implements ExternalAlarmClient{

    @Override
    public void sendYoutubeAndNewsMessage(List<YoutubeAndNewsAlarmDto> youtubeAndNewsAlarmDtoList, long userIdx) {

    }

    @Override
    public void sendJobMessage(List<JobAlarmDto> jobAlarmDtoList, long userIdx) {

    }

}
