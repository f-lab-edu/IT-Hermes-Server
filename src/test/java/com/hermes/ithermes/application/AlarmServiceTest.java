package com.hermes.ithermes.application;

import com.hermes.ithermes.infrastructure.JobRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AlarmServiceTest {

    @Value("${telegram-key}")
    private String telegramKey;

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
        TelegramBot telegramBot=new TelegramBot(telegramKey);
        alarmService.sendJobAlarmMessage(jobRepository.getJobAlarm(1L),1L);
    }

    @Test
    @DisplayName("유저 id값 1을 가진 유저에게 유투브,뉴스 알림을 전송가는지 테스트")
    public void telegramYoutubeAndNewsTest(){
        TelegramBot telegramBot=new TelegramBot(telegramKey);
        alarmService.sendYoutubeAndNewsMessage(youtubeAndNewsRepository.getYoutubeAndNewsAlarm(1L),1L);
    }

}