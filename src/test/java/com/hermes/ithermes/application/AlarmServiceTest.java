package com.hermes.ithermes.application;

import com.hermes.ithermes.infrastructure.JobRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AlarmServiceTest {

    private final JobRepository jobRepository;
    private final YoutubeAndNewsRepository youtubeAndNewsRepository;
    private final AlarmService alarmService;

    @Autowired
    public AlarmServiceTest(JobRepository jobRepository, YoutubeAndNewsRepository youtubeAndNewsRepository, AlarmService alarmService) {
        this.jobRepository = jobRepository;
        this.youtubeAndNewsRepository = youtubeAndNewsRepository;
        this.alarmService = alarmService;
    }

    @Test
    @DisplayName("유저 id값 1을 가진 유저에게 채용정보 알림을 전송가는지 테스트")
    public void telegramJobAlarmTest(){
        alarmService.sendJobAlarmMessage(jobRepository.getJobAlarmContents(1L),1L);
    }

    @Test
    @DisplayName("유저 id값 1을 가진 유저에게 유투브,뉴스 알림을 전송가는지 테스트")
    public void telegramYoutubeAndNewsTest(){
        alarmService.sendYoutubeAndNewsMessage(youtubeAndNewsRepository.getYoutubeAndNewsAlarmContents(1L),1L);
    }

}