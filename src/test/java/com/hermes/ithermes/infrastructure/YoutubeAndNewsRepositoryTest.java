package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.application.AlarmService;
import com.hermes.ithermes.domain.entity.ContentsProvider;
import com.hermes.ithermes.domain.entity.Subscribe;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.util.ActiveType;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import com.hermes.ithermes.domain.util.JobType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
class YoutubeAndNewsRepositoryTest {

    private final YoutubeAndNewsJpaRepository youtubeAndNewsJpaRepository;
    private final SubscribeRepository subscribeRepository;
    private final UserRepository userRepository;
    private final ContentsProviderRepository contentsProviderRepository;
    private final AlarmService alarmService;

    @Autowired
    public YoutubeAndNewsRepositoryTest(YoutubeAndNewsJpaRepository youtubeAndNewsJpaRepository, SubscribeRepository subscribeRepository, UserRepository userRepository, ContentsProviderRepository contentsProviderRepository, AlarmService alarmService) {
        this.youtubeAndNewsJpaRepository = youtubeAndNewsJpaRepository;
        this.subscribeRepository = subscribeRepository;
        this.userRepository = userRepository;
        this.contentsProviderRepository = contentsProviderRepository;
        this.alarmService = alarmService;
    }

    @Test
    @DisplayName("DB상에 유저아이디 번호 1번인 유저가 구독한 youtubeandnews의 구독정보는 4개인지 테스트")
    public void YoutubeAndNewsContentsAlarmTest(){

        youtubeAndNewsJpaRepository.deleteAll();
        userRepository.deleteAll();
        subscribeRepository.deleteAll();
        contentsProviderRepository.deleteAll();

        User user1 = new User(1L,"이은영","eun02323","12345", JobType.BACKEND,0,"123455235",false);
        userRepository.save(user1);

        ContentsProvider contentsProvider = new ContentsProvider(1l, CategoryType.NEWS, ContentsProviderType.NOMAD_CODERS);
        contentsProviderRepository.save(contentsProvider);

        Subscribe subscribe = new Subscribe(1L,user1,contentsProvider,1,4,JobType.BACKEND, ActiveType.ACTIVE);
        subscribeRepository.save(subscribe);

        for(int i = 0; i < 4; i++){
            YoutubeAndNews youtubeAndNews = new YoutubeAndNews((long) i,contentsProvider,"안녕하세요","ㅎㅎㅎㅎㅎㅎ","이미지 url","그냥 url", LocalDateTime.now(),123L,false);
            youtubeAndNewsJpaRepository.save(youtubeAndNews);
        }

        Assertions.assertEquals(4,alarmService.getYoutubeAndNewsAlarmDto(1l));
    }


}