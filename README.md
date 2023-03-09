# PROJECT IT-Hermes

:paperclip: [서비스 링크](https://it-hermes.store)  

:paperclip: [IT-Hermes 크롤링서버 레포](https://github.com/f-lab-edu/IT-Hermes-Crawling)  
:paperclip: [IT-Hermes 배치서버 레포](https://github.com/f-lab-edu/IT-Hermes-Batch)  
:paperclip: [IT-Hermes 프론트 레포](https://github.com/f-lab-edu/IT-Hermes-Front)    
<br>

## :thought_balloon: IT 관련 컨텐츠 알림 서비스  

```프로젝트 기간: 2023.01.04 ~```

> IT에 관심이 많은 유저에게 채용, 유투브, 뉴스 정보 등의 각종 IT 관련 컨텐츠를   
> 유저 맞춤형으로 자동 알림해주는 서비스

- 20분 간격으로 배치서버를 통해 크롤링 서버를 호출해서 최신 IT 관련 컨텐츠를 수집  
- 객체지향 원리와 디자인 패턴을 적용하여 코드 작성
- 깃허브 액션으로 CI, 젠킨스로 CD 구축
- 성능 부하 테스트를 통해 트랜잭션 처리 시간을 측정해서 처리 속도 개선
- 스프링 시큐리티를 적용해서 인증 관련 로직을 반복을 최소화하면서 기능 구현


<br>

## :page_facing_up: 기술 스택  


`spring boot 3.0`, `Java 17`  
`spring security`  
`Mysql 8.0`,`Flyway`,`h2`,`JPA`  
`Jenkins`, `Github action`  
`Docker`, `Docker hub`  
`Nodejs`  
`Feign Client`  
`Html`,`CSS`,`Javascript`  
`Github`    




## :thought_balloon: CI/CD 구조  
![BEA9BE37-622E-4D68-91C0-7B62ACCA92EE](https://user-images.githubusercontent.com/70764912/224013250-d7b65d5b-8af2-44af-bcfb-15ce072db496.jpeg)



## :thought_balloon: 애플리케이션 프로세스 구조    
![7B15936F-7D34-4358-B5FA-7B80E1B7F4B8_1_105_c](https://user-images.githubusercontent.com/70764912/224013410-1cc5e532-2629-4ce4-a6bc-bfec43fd6d3b.jpeg)


## :thought_balloon: 배치/크롤링 구조    
![608230A8-9C2E-4732-ACDE-0D46017FA8D9_1_105_c](https://user-images.githubusercontent.com/70764912/224013526-201a520d-e82d-42d2-be80-3c9858884675.jpeg)


## :thought_balloon: 구독/추천정보 알림 로직 구조    
![97897687-1851-4335-A423-460CE163D504](https://user-images.githubusercontent.com/70764912/224013743-50b78866-ee92-4348-863d-7e08aa10c73d.jpeg)




