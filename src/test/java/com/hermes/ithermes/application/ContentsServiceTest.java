package com.hermes.ithermes.application;


import com.hermes.ithermes.domain.entity.ContentsEntityInterface;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsRepository;
import com.hermes.ithermes.presentation.dto.contents.ContentsDtoInterface;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
/*
@SpringBootTest
@Transactional
class ContentsServiceTest {

    @Autowired
    private ContentsService contentsService;

    @Autowired
    private YoutubeAndNewsRepository youtubeAndNewsRepository;

    @BeforeEach
    void setUp() {
        for(int i=0; i<12; i++){
            YoutubeAndNews youtubeAndNews = new YoutubeAndNews((long)i,"안녕하세요","ㅎㅎㅎㅎㅎ","이미지 url","그냥 url", LocalDateTime.now(),123L,false,CategoryType.YOUTUBE, ContentsProviderType.DREAM_CODING);
            youtubeAndNewsRepository.save(youtubeAndNews);
        }
    }

    @Test
    @DisplayName("main contents를 10개 반환하는지 테스트")
    void checkMainContentsCount(){
        List<ContentsDtoInterface> results = contentsService.getMainContents(CategoryType.YOUTUBE);
        Assertions.assertEquals(10, results.size());
    }

    @Test
    @DisplayName("category contents 페이징 처리 테스트")
    void checkpaging() {
        Pageable pageInfo = PageRequest.of(0,2);
        List<ContentsEntityInterface> youtubeContents = youtubeAndNewsRepository.findYoutubeAndNewsByCategory(pageInfo, CategoryType.YOUTUBE).getContent();

        Assertions.assertEquals(2,youtubeContents.size());
    }

}*/

