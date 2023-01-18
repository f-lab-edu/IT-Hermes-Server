package com.hermes.ithermes.infrastructure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YoutubeAndNewsRepositoryTest {

    private final YoutubeAndNewsRepository youtubeAndNewsRepository;

    @Autowired
    public YoutubeAndNewsRepositoryTest(YoutubeAndNewsRepository youtubeAndNewsRepository) {
        this.youtubeAndNewsRepository = youtubeAndNewsRepository;
    }

    @Test
    @DisplayName("DB상에 유저아이디 번호 1번인 유저가 구독한 youtubeandnews의 구독정보는 4개인지 테스트")
    public void YoutubeAndNewsContentsAlarmTest(){
        Assertions.assertEquals(4,youtubeAndNewsRepository.getYoutubeAndNewsAlarmContents(1L).size());
    }


}