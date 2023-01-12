package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Service;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.domain.util.ContentsType;
import com.hermes.ithermes.infrastructure.ServiceRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewsRepository;
import com.hermes.ithermes.presentation.dto.contents.MainPageContentsDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@SpringBootTest
@Transactional
class ContentsServiceTest {

    @Autowired
    private ContentsService contentsService;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private YoutubeAndNewsRepository youtubeAndNewsRepository;

    @BeforeEach
    void setUp() {
        Service service1=new Service(1L,false,"노마드","youtube");
        Service service2=new Service(2L,false,"드림코딩엘리","youtube");
        Service service3=new Service(3L,false,"네이버뉴스","news");
        Service service4=new Service(4L,false,"요즘 IT","news");
        Service service5=new Service(5L,false,"코딩월드뉴스","news");

        Service s1=serviceRepository.save(service1);
        Service s2=serviceRepository.save(service2);
        Service s3=serviceRepository.save(service3);
        Service s4=serviceRepository.save(service4);
        Service s5=serviceRepository.save(service5);

        YoutubeAndNews youtubeAndNews1=new YoutubeAndNews(1l,"이 파이썬 모듈은","이 파이썬 모듈은 시간을 아껴줍니다.","https://asdfs","https://asdfs",
                false,"20221222",100L,s1);
        YoutubeAndNews youtubeAndNews2=new YoutubeAndNews(2l,"웹 사이트에 눈을 내려보자","bbbbbbbbb","https://asdfs","https://asdfs",
                false,"20220122",30L,s1);
        YoutubeAndNews youtubeAndNews3=new YoutubeAndNews(3l,"웹 트렌드","꼭 알아야 할 웹 트렌드","https://aooo","https://asdfs",
                false,"20220323",50L,s2);
        YoutubeAndNews youtubeAndNews4=new YoutubeAndNews(4l,"자바스크립트","자바스크립트로 코딩할떄 꿀팁","https://aooo","https://asdfs",
                false,"20211231",50L,s2);
        YoutubeAndNews youtubeAndNews5=new YoutubeAndNews(5l,"클라우드","클라우드 업계","https://aooo","https://asdfs",
                false,"20101222",300L,s5);
        YoutubeAndNews youtubeAndNews6=new YoutubeAndNews(6l,"빅테크","구조조정 잇따라","https://aooo","https://asdfs",
                false,"20140515",400L,s4);
        YoutubeAndNews youtubeAndNews7=new YoutubeAndNews(7l,"폐인 코인","은행 계좌 못구한","https://uuuu","https://asdfs",
                false,"20220405",500L,s5);
        YoutubeAndNews youtubeAndNews8=new YoutubeAndNews(8l,"hhhh","hhhhhhh","https://uuuu","https://asdfs",
                false,"20140506",400L,s4);
        YoutubeAndNews youtubeAndNews9=new YoutubeAndNews(9l,"티오베","급상승 언어로 c++선정","https://uuuu","https://asdfs",
                false,"20120222",600L,s3);
        YoutubeAndNews youtubeAndNews10=new YoutubeAndNews(10l,"kkkk","kkkkkkk","https://uuuu","https://asdfs",
                false,"20220304",1000L,s4);
        YoutubeAndNews youtubeAndNews11=new YoutubeAndNews(11l,"개발자라면","이런 아바타를 써야지","https://uuuu","https://asdfs",
                false,"20220506",500L,s2);
        YoutubeAndNews youtubeAndNews12=new YoutubeAndNews(12l,"oooo","ooooooo","https://uuuu","https://asdfs",
                false,"20200304",100L,s5);

        youtubeAndNewsRepository.save(youtubeAndNews1);
        youtubeAndNewsRepository.save(youtubeAndNews2);
        youtubeAndNewsRepository.save(youtubeAndNews3);
        youtubeAndNewsRepository.save(youtubeAndNews4);
        youtubeAndNewsRepository.save(youtubeAndNews5);
        youtubeAndNewsRepository.save(youtubeAndNews6);
        youtubeAndNewsRepository.save(youtubeAndNews7);
        youtubeAndNewsRepository.save(youtubeAndNews8);
        youtubeAndNewsRepository.save(youtubeAndNews9);
        youtubeAndNewsRepository.save(youtubeAndNews10);
        youtubeAndNewsRepository.save(youtubeAndNews11);
        youtubeAndNewsRepository.save(youtubeAndNews12);
    }

    @Test
    @DisplayName("main contents를 10개 반환하는지 테스트")
    void checkMainContentsCount(){
        List<MainPageContentsDto> results=contentsService.getMainContents(ContentsType.YOUTUBEANDNEWS.getName());
        Assertions.assertEquals(10,results.size());
    }

    @Test
    @DisplayName("category contents 페이징 처리 테스트")
    void checkpaging() {
        PageRequest pageRequest = PageRequest.of(0,2);

        Page<YoutubeAndNews> youtubeAndNewsResults = youtubeAndNewsRepository.findYoutubeAndNewsByCategory(pageRequest,"youtube");

        Assertions.assertEquals(2,youtubeAndNewsResults.getContent().size());
        Assertions.assertTrue(youtubeAndNewsResults.isFirst());
        Assertions.assertTrue(youtubeAndNewsResults.hasNext());
    }
}