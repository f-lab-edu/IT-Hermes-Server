package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.application.AlarmService;
import com.hermes.ithermes.domain.entity.ContentsProvider;
import com.hermes.ithermes.domain.entity.Job;
import com.hermes.ithermes.domain.entity.User;
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
class JobRepositoryTest {

    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final JobJpaRepository jobJpaRepository;
    private final ContentsProviderRepository contentsProviderRepository;
    private final AlarmService alarmService;

    @Autowired
    public JobRepositoryTest(UserRepository userRepository, SubscribeRepository subscribeRepository, JobJpaRepository jobJpaRepository, ContentsProviderRepository contentsProviderRepository, AlarmService alarmService) {
        this.userRepository = userRepository;
        this.subscribeRepository = subscribeRepository;
        this.jobJpaRepository = jobJpaRepository;
        this.contentsProviderRepository = contentsProviderRepository;
        this.alarmService = alarmService;
    }

    @Test
    @DisplayName("DB상에 유저아이디 번호 1번인 유저가 구독한 job의 구독정보는 3개인지 테스트")
    public void JobContentsAlarmTest(){

        userRepository.deleteAll();
        subscribeRepository.deleteAll();
        jobJpaRepository.deleteAll();

        User user1 = new User(1L,"이은영","eun02323","12345", JobType.BACKEND,0,"123455235",false);
        userRepository.save(user1);

        ContentsProvider contentsProvider = new ContentsProvider(1l, CategoryType.JOB, ContentsProviderType.SARAMIN);
        contentsProviderRepository.save(contentsProvider);

        for(int i = 0; i < 3; i++){
            Job job = new Job((long) i,contentsProvider,"안녕하세요","url 주소","서울","회사명", LocalDateTime.now(),LocalDateTime.now(),123l,false);
            jobJpaRepository.save(job);
        }

        Assertions.assertEquals(3,alarmService.getJobAlarmDto(1l));
    }

}