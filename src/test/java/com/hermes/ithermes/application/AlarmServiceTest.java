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
    @DisplayName("유저아이디 번호 1번인 유저가 구독한 job의 구독정보는 3개인지 테스트")
    void subscribeJobAlarmCountTest(){
        userRepository.deleteAll();
        subscribeRepository.deleteAll();
        jobRepository.deleteAll();

        User user1 = new User(1L,"이은영","eun02323","12345", JobType.BACKEND,0,"123455235",false,"eysdfetwetwe");
        userRepository.save(user1);

        for(int i = 0; i < 3; i++){
            Job job = new Job((long) i,"안녕하세요","url 주소","서울","회사명", LocalDateTime.now(),LocalDateTime.now(),123l,false, ContentsProviderType.NAVER, GradeType.BEGINNER);
            jobRepository.save(job);
        }

        Subscribe subscribe = new Subscribe(1L, user1, ActiveType.ACTIVE, CategoryType.JOB, ContentsProviderType.NAVER,"마지막 url");
        subscribeRepository.save(subscribe);

        Assertions.assertEquals(3, alarmService.getUserJobAlarmContents(1l).size());
    }

    @Test
    @DisplayName("유저아이디 번호 1번인 유저가 구독한 youtube의 구독정보는 4개인지 테스트")
    void subscribeYoutubeAlarmCountTest(){
        youtubeAndNewsRepository.deleteAll();
        userRepository.deleteAll();
        subscribeRepository.deleteAll();

        User user1 = new User(1L,"이은영","eun02323","12345", JobType.BACKEND,0,"123455235",false,"eysdfetwetwe");
        userRepository.save(user1);

        Subscribe subscribe = new Subscribe(1L, user1, ActiveType.ACTIVE, CategoryType.YOUTUBE, ContentsProviderType.DREAM_CODING,"마지막 url");
        subscribeRepository.save(subscribe);

        for(int i = 0; i < 4; i++){
            YoutubeAndNews youtubeAndNews = new YoutubeAndNews((long) i,"안녕하세요","ㅎㅎㅎㅎㅎㅎ","이미지 url","그냥 url", LocalDateTime.now(),123L,false,CategoryType.YOUTUBE,ContentsProviderType.DREAM_CODING);
            youtubeAndNewsRepository.save(youtubeAndNews);
        }

        Assertions.assertEquals(4, alarmService.getUserYoutubeAndNewsAlarmContents(1l,CategoryType.YOUTUBE).size());
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