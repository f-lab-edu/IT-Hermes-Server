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
- 30분 간격으로 배치서버를 통해 애플리케이션 서버를 호출해서 유저 구독 정보 알림 제공  
- 객체지향 원리와 디자인 패턴을 적용하여 코드 작성  
- 깃허브 액션으로 CI, 젠킨스로 CD 구축  
- 성능 부하 테스트를 통해 트랜잭션 처리 시간을 측정해서 처리 속도 개선  
- 스프링 시큐리티를 적용해서 인증 관련 로직을 반복을 최소화하면서 기능 구현  
- RabbitMQ를 이용해서 Batch 서버와 Crawling 서버 장애 대응  
- ElasticSearch를 이용해서 검색 트랜잭션 처리속도 개선 경험  

<br>

## :page_facing_up: 기술 스택  


`spring boot 3.0`, `Java 17`  
`spring security`  
`Mysql 8.0`,`h2`,`Spring Data JPA`  
`Jenkins`, `Github action`  
`Docker`, `Docker hub`  
`Nodejs, NCP`  
`Feign Client`  
`Html`,`CSS`,`Javascript`  
`RabbitMQ`,`ElasticSearch`   
`Redis`,`EH Cache`

## :thought_balloon: 전체 구조    
![68A30EE6-7C85-4FD7-B51C-AFB90683363D_1_105_c](https://user-images.githubusercontent.com/70764912/230696042-70781f1d-6f8f-46d4-9e45-86ad4fa57cb5.jpeg)

<br>

<br>

## :page_facing_up: 트러블슈팅 개선



