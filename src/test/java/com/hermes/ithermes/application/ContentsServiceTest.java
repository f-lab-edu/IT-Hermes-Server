package com.hermes.ithermes.application;


import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsRepository;
import com.hermes.ithermes.presentation.dto.contents.ContentsDtoInterface;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Transactional
class ContentsServiceTest {

    @Autowired
    private ContentsService contentsService;

    @Autowired
    private YoutubeAndNewsRepository youtubeAndNewsRepository;

    @BeforeEach
    void setUp() {

//        for(int i=0; i<12; i++){
//            YoutubeAndNews youtubeAndNews=new YoutubeAndNews((long) i,s1,"이 파이썬 모듈은 시간을 아껴줍니다.","ㅎㅎㅎㅎㅎㅎ","https://asdfs",
//                    "https://naver.com", LocalDateTime.now(),100L,false);
//            youtubeAndNewsRepository.save(youtubeAndNews);
//        }
    }

    @Test
    @DisplayName("main contents를 10개 반환하는지 테스트")
    void checkMainContentsCount(){
        List<ContentsDtoInterface> results=contentsService.getMainContents(CategoryType.YOUTUBE_AND_NEWS);
        Assertions.assertEquals(10,results.size());
    }

//    @Test
//    @DisplayName("category contents 페이징 처리 테스트")
//    void checkpaging() {
//        Pageable pageInfo = PageRequest.of(0,2);
//        List<ContentsEntityInterface> youtubeContents=youtubeAndNewsRepository.findYoutubeAndNewsBySorting(pageInfo,ContentsType.YOUTUBE, OrderType.POPULAR);
//
//        Assertions.assertEquals(2,youtubeContents.size());
//    }

}



