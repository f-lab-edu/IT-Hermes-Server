package com.hermes.ithermes.presentation;

import com.hermes.ithermes.application.ContentsService;
import com.hermes.ithermes.domain.entity.Service;
import com.hermes.ithermes.domain.entity.YoutubeAndNews;
import com.hermes.ithermes.infrastructure.ServiceRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewRepository;
import com.hermes.ithermes.infrastructure.YoutubeAndNewRepository.*;
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
class ContentsControllerTest {

    @Autowired
    private ContentsService contentsService;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private YoutubeAndNewRepository youtubeAndNewRepository;

    @BeforeEach
    void setUp() {
        Service service1=new Service(1L,false,"노마드","유투브");
        Service service2=new Service(2L,false,"드림코딩엘리","유투브");
        Service service3=new Service(3L,false,"네이버뉴스","뉴스");
        Service service4=new Service(4L,false,"요즘 IT","뉴스");
        Service service5=new Service(5L,false,"코딩월드뉴스","뉴스");

        Service s1=serviceRepository.save(service1);
        Service s2=serviceRepository.save(service2);
        Service s3=serviceRepository.save(service3);
        Service s4=serviceRepository.save(service4);
        Service s5=serviceRepository.save(service5);

        YoutubeAndNews youtubeAndNews1=new YoutubeAndNews(1l,"aaa","aaaaaaaaaa","https://asdfs","https://asdfs",
                false,"20221222",100L,s1);
        YoutubeAndNews youtubeAndNews2=new YoutubeAndNews(2l,"bbb","bbbbbbbbb","https://asdfs","https://asdfs",
                false,"20220122",30L,s1);
        YoutubeAndNews youtubeAndNews3=new YoutubeAndNews(3l,"ccc","cccccccccc","https://aooo","https://asdfs",
                false,"20220323",50L,s2);
        YoutubeAndNews youtubeAndNews4=new YoutubeAndNews(4l,"ddd","ddddddddd","https://aooo","https://asdfs",
                false,"20211231",50L,s2);
        YoutubeAndNews youtubeAndNews5=new YoutubeAndNews(5l,"eee","eeeeeeeee","https://aooo","https://asdfs",
                false,"20101222",300L,s5);
        YoutubeAndNews youtubeAndNews6=new YoutubeAndNews(6l,"ffff","fffffffff","https://aooo","https://asdfs",
                false,"20140515",400L,s4);
        YoutubeAndNews youtubeAndNews7=new YoutubeAndNews(7l,"gggg","gggggggggg","https://uuuu","https://asdfs",
                false,"20220405",500L,s5);
        YoutubeAndNews youtubeAndNews8=new YoutubeAndNews(8l,"hhhh","hhhhhhh","https://uuuu","https://asdfs",
                false,"20140506",400L,s4);
        YoutubeAndNews youtubeAndNews9=new YoutubeAndNews(9l,"jjjj","jjjjjjj","https://uuuu","https://asdfs",
                false,"20120222",600L,s3);
        YoutubeAndNews youtubeAndNews10=new YoutubeAndNews(10l,"kkkk","kkkkkkk","https://uuuu","https://asdfs",
                false,"20220304",1000L,s4);
        YoutubeAndNews youtubeAndNews11=new YoutubeAndNews(11l,"pppp","ppppppp","https://uuuu","https://asdfs",
                false,"20220506",500L,s2);
        YoutubeAndNews youtubeAndNews12=new YoutubeAndNews(12l,"oooo","ooooooo","https://uuuu","https://asdfs",
                false,"20200304",100L,s5);

        youtubeAndNewRepository.save(youtubeAndNews1);
        youtubeAndNewRepository.save(youtubeAndNews2);
        youtubeAndNewRepository.save(youtubeAndNews3);
        youtubeAndNewRepository.save(youtubeAndNews4);
        youtubeAndNewRepository.save(youtubeAndNews5);
        youtubeAndNewRepository.save(youtubeAndNews6);
        youtubeAndNewRepository.save(youtubeAndNews7);
        youtubeAndNewRepository.save(youtubeAndNews8);
        youtubeAndNewRepository.save(youtubeAndNews9);
        youtubeAndNewRepository.save(youtubeAndNews10);
        youtubeAndNewRepository.save(youtubeAndNews11);
        youtubeAndNewRepository.save(youtubeAndNews12);
    }

    @Test
    @DisplayName("main contents를 10개 반환하는지 테스트")
    void checkMainContentsCount(){
        List<MainContents> results=contentsService.getMainContents();
        Assertions.assertEquals(10,results.size());
    }
    /*
    @Test
    @DisplayName("main contents가 viewcount 내림차순으로 반환되는지 테스트")
    void checkMainContentsViewCountOrder(){
        List<YoutubeAndNewRepository.MainContents> results=contentsService.getMainContents();
    }*/

}