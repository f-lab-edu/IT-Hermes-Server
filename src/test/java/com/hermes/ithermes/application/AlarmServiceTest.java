package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.entity.Subscribe;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.util.*;
import com.hermes.ithermes.infrastructure.JobRepository;
import com.hermes.ithermes.infrastructure.SubscribeRepository;
import com.hermes.ithermes.infrastructure.UserRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@SpringBootTest
@Transactional
class AlarmServiceTest {

    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final JobRepository jobRepository;
    private final YoutubeAndNewsRepository youtubeAndNewsRepository;
    private final AlarmService alarmService;
    private final ExternalAlarmClient externalAlarmClient;

    @Autowired
    public AlarmServiceTest(UserRepository userRepository, SubscribeRepository subscribeRepository, JobRepository jobRepository, YoutubeAndNewsRepository youtubeAndNewsRepository, AlarmService alarmService, ExternalAlarmClient externalAlarmClient) {
        this.userRepository = userRepository;
        this.subscribeRepository = subscribeRepository;
        this.jobRepository = jobRepository;
        this.youtubeAndNewsRepository = youtubeAndNewsRepository;
        this.alarmService = alarmService;
        this.externalAlarmClient = externalAlarmClient;
    }

    @Test
    @DisplayName("유저 id값 1을 가진 유저에게 채용정보 알림을 전송가는지 테스트")
    public void telegramJobAlarmTest(){
       externalAlarmClient.sendJobMessage(alarmService.getUserJobAlarmContents(1L),1L);
    }

    @Test
    @DisplayName("유저 id값 1을 가진 유저에게 유투브 알림을 전송가는지 테스트")
    public void telegramYoutubeAndNewsTest(){
        externalAlarmClient.sendYoutubeMessage(alarmService.getUserYoutubeAlarmContents(1L),1L);
    }

    @Test
    @DisplayName("유저아이디 번호 1번인 유저가 구독한 job의 구독정보는 3개인지 테스트")
    void SubscribeJobAlarmCountTest(){
        userRepository.deleteAll();
        subscribeRepository.deleteAll();
        jobRepository.deleteAll();

        User user1 = new User(1L,"이은영","eun02323","12345", JobType.BACKEND,0,"123455235",false);
        userRepository.save(user1);

        for(int i = 0; i < 3; i++){
            Job job = new Job((long) i,"안녕하세요","url 주소","서울","회사명", LocalDateTime.now(),LocalDateTime.now(),123l,false, ContentsProviderType.NAVER, GradeType.BEGINNER);
            jobRepository.save(job);
        }

        Subscribe subscribe = new Subscribe(1L, user1, ActiveType.ACTIVE, CategoryType.JOB, ContentsProviderType.NAVER);
        subscribeRepository.save(subscribe);

        Assertions.assertEquals(3, alarmService.getUserJobAlarmContents(1l).size());
    }

    @Test
    @DisplayName("유저아이디 번호 1번인 유저가 구독한 youtube의 구독정보는 4개인지 테스트")
    void SubscribeYoutubeAlarmCountTest(){
        youtubeAndNewsRepository.deleteAll();
        userRepository.deleteAll();
        subscribeRepository.deleteAll();

        User user1 = new User(1L,"이은영","eun02323","12345", JobType.BACKEND,0,"123455235",false);
        userRepository.save(user1);

        Subscribe subscribe = new Subscribe(1L, user1, ActiveType.ACTIVE, CategoryType.YOUTUBE, ContentsProviderType.DREAM_CODING);
        subscribeRepository.save(subscribe);

        for(int i = 0; i < 4; i++){
            YoutubeAndNews youtubeAndNews = new YoutubeAndNews((long) i,"안녕하세요","ㅎㅎㅎㅎㅎㅎ","이미지 url","그냥 url", LocalDateTime.now(),123L,false,CategoryType.YOUTUBE,ContentsProviderType.DREAM_CODING);
            youtubeAndNewsRepository.save(youtubeAndNews);
        }

        Assertions.assertEquals(4, alarmService.getUserYoutubeAlarmContents(1l).size());
    }

}