package com.hermes.ithermes.application;

/*
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
        Service service1=new Service(1L,false,"노마드", CategoryType.YOUTUBE);
        Service s1=serviceRepository.save(service1);

        for(int i=0; i<12; i++){
            YoutubeAndNews youtubeAndNews=new YoutubeAndNews((long) i,"이 파이썬 모듈은","이 파이썬 모듈은 시간을 아껴줍니다.","https://asdfs","https://asdfs",
                    false, LocalDateTime.now(),100L,s1);
            youtubeAndNewsRepository.save(youtubeAndNews);
        }
    }

    @Test
    @DisplayName("main contents를 10개 반환하는지 테스트")
    void checkMainContentsCount(){
        List<DtoInterface> results=contentsService.getMainContents(CategoryType.YOUTUBE_AND_NEWS);
        Assertions.assertEquals(10,results.size());
    }

    @Test
    @DisplayName("category contents 페이징 처리 테스트")
    void checkpaging() {
        PageRequest pageRequest = PageRequest.of(0,2);

        Page<YoutubeAndNews> youtubeAndNewsResults = (Page<YoutubeAndNews>) youtubeAndNewsRepository.findYoutubeAndNewsBySorting(pageRequest,CategoryType.YOUTUBE, OrderType.valueOf(""));

        Assertions.assertEquals(2,youtubeAndNewsResults.getContent().size());
        Assertions.assertTrue(youtubeAndNewsResults.isFirst());
        Assertions.assertTrue(youtubeAndNewsResults.hasNext());
    }
}*/



