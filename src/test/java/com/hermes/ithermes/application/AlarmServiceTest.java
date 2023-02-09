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
        externalAlarmClient.sendContentsMessage(alarmService.getUserYoutubeAndNewsAlarmContents(1L,CategoryType.YOUTUBE),1L);
    }

    @Test
    @DisplayName("유저 JOB이 백엔드 경우인 경우 키워드가 8개인지 테스트")
    void userCustomKeywordsTest(){
        User user1 = new User(1l,"은영","eun0232","eun232323",JobType.BACKEND,1,"telgramasdf",false,"eysdfetwetwe");
        userRepository.save(user1);
        Assertions.assertEquals(8, alarmService.getRecommendKeywords(1l).size());
    }

    @Test
    @DisplayName("유저 JOB이 백엔드인 경우 키워드에 맞는 youtube 컨텐츠가 반환되는지 테스트")
    void youtubeAndNewsRecommendAlarmTest(){
        User user1 = new User(1l,"은영","eun0232","eun232323",JobType.BACKEND,1,"telgramasdf",false,"eysdfetwetwe");
        userRepository.save(user1);

        YoutubeAndNews youtubeAndNews1 = new YoutubeAndNews(1l,"API","내용","이미지 url","그냥 url",LocalDateTime.now(),123l,false,CategoryType.YOUTUBE,ContentsProviderType.DREAM_CODING);
        YoutubeAndNews youtubeAndNews2 = new YoutubeAndNews(2L,"ㅎㅎㅎㅎㅎ","내용","이미지 url","그냥 url",LocalDateTime.now(),50l,false,CategoryType.YOUTUBE,ContentsProviderType.DREAM_CODING);
        YoutubeAndNews youtubeAndNews3 = new YoutubeAndNews(3L,"오픈소스","내용","이미지 url","그냥 url",LocalDateTime.now(),100l,false,CategoryType.YOUTUBE,ContentsProviderType.DREAM_CODING);
        youtubeAndNewsRepository.save(youtubeAndNews1);
        youtubeAndNewsRepository.save(youtubeAndNews2);
        youtubeAndNewsRepository.save(youtubeAndNews3);

        Subscribe subscribe1 = new Subscribe(1l,user1,ActiveType.ACTIVE,CategoryType.YOUTUBE,ContentsProviderType.DREAM_CODING,null);
        subscribeRepository.save(subscribe1);

        Assertions.assertEquals(2,alarmService.getUserRecommendAlarmContents(1l,CategoryType.YOUTUBE).size());
    }




}