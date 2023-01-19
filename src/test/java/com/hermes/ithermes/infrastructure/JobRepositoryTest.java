package com.hermes.ithermes.infrastructure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JobRepositoryTest {

    private final JobRepository jobRepository;

    @Autowired
    public JobRepositoryTest(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Test
    @DisplayName("DB상에 유저아이디 번호 1번인 유저가 구독한 job의 구독정보는 3개인지 테스트")
    public void JobContentsAlarmTest(){
        Assertions.assertEquals(3,jobRepository.getJobAlarmContents(1L).size());
    }

}