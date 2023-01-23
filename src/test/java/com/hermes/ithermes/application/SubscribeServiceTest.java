package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Subscribe;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.exception.WrongIdOrPasswordException;
import com.hermes.ithermes.domain.factory.SubscribeFactory;
import com.hermes.ithermes.domain.util.ActiveType;
import com.hermes.ithermes.domain.util.CategoryType;
import com.hermes.ithermes.domain.util.ContentsProviderType;
import com.hermes.ithermes.domain.util.JobType;
import com.hermes.ithermes.infrastructure.SubscribeRepository;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.subscribe.SubscribeContentsDto;
import com.hermes.ithermes.presentation.dto.subscribe.SubscribeFindSubscribeRequestDto;
import com.hermes.ithermes.presentation.dto.subscribe.SubscribePutSubscribeRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscribeServiceTest {
    @InjectMocks
    private SubscribeService subscribeService;
    @Mock
    private SubscribeRepository subscribeRepository;
    @Mock
    private SubscribeFactory subscribeFactory;

    private Subscribe subscribe;
    private User user;
    ArrayList<SubscribeContentsDto> subscribeContentsList;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .loginId("test").nickname("김승기")
                .password("test1234").job(JobType.BACKEND)
                .yearOfExperience(1)
                .build();

        subscribe = Subscribe.builder()
                .user(user)
                .category(CategoryType.JOB)
                .contentsProvider(ContentsProviderType.SARAMIN)
                .isActive(ActiveType.ACTIVE)
                .build();

        subscribeContentsList = new ArrayList<>();
        subscribeContentsList.add(new SubscribeContentsDto("SARAMIN","ACTIVE"));
        subscribeContentsList.add(new SubscribeContentsDto("WANTED","ACTIVE"));
        subscribeContentsList.add(new SubscribeContentsDto("CODING_WORLD","ACTIVE"));
        subscribeContentsList.add(new SubscribeContentsDto("NAVER","ACTIVE"));
        subscribeContentsList.add(new SubscribeContentsDto("YOZM","ACTIVE"));
        subscribeContentsList.add(new SubscribeContentsDto("NOMAD_CODERS","NON_ACTIVE"));
        subscribeContentsList.add(new SubscribeContentsDto("DREAM_CODING","ACTIVE"));

    }

    @Test
    @DisplayName("구독 데이터를 입력 시, 구독 테이블에 요청 된 정보 등록 혹은 수정 성공")
    void 구독_PUT_정상처리() {
        SubscribePutSubscribeRequestDto subscribePutSubscribeRequestDto = new SubscribePutSubscribeRequestDto("test", subscribeContentsList);
        assertEquals(new CommonResponseDto().getMessage(), subscribeService.putSubscribe(subscribePutSubscribeRequestDto).getMessage());
    }

    @Test
    @DisplayName("구독 데이터 중 로그인 아이디를 잘못 입력할 시, WrongIdOrPasswordException 던지며 구독 정보 등록 혹은 수정 실패")
    void 구독_PUT_실패처리() {
        SubscribePutSubscribeRequestDto subscribePutSubscribeRequestDto = new SubscribePutSubscribeRequestDto("test", subscribeContentsList);

        when(subscribeFactory.parsePutSubscribeDtoToSubscribes(any())).thenThrow(new WrongIdOrPasswordException());

        assertThrows(WrongIdOrPasswordException.class, () -> subscribeService.putSubscribe(subscribePutSubscribeRequestDto).getMessage());
    }

    @Test
    @DisplayName("유저의 아이디를 요청할 시, 유저 정보와 일치하는 구독 테이블 정보가 있다면 조회 성공")
    void 구독조회_존재하는_구독데이터_조회() {
        SubscribeFindSubscribeRequestDto subscribeFindSubscribeRequestDto = new SubscribeFindSubscribeRequestDto("test");
        List<Subscribe> list = new ArrayList<>();
        list.add(subscribe);
        when(subscribeFactory.parseFindSubscribeDtoToSubscribes(any())).thenReturn(list);

        List<SubscribeContentsDto> responseSubscribe = subscribeService.findSubscribe(subscribeFindSubscribeRequestDto);
        for (int i = 0; i < responseSubscribe.size(); i++) {
            assertEquals(subscribeContentsList.get(i).getIsActive(), responseSubscribe.get(i).getIsActive());
        }
    }

    @Test
    @DisplayName("유저의 아이디를 요청할 시, 유저 정보와 일치하는 구독 테이블 정보가 있다면 비활성화 데이터를 보내며 조회 성공")
    void 구독조회_존재하지않는_구독데이터_조회() {
        SubscribeFindSubscribeRequestDto subscribeFindSubscribeRequestDto = new SubscribeFindSubscribeRequestDto("test");
        List<Subscribe> list = new ArrayList<>();
        when(subscribeFactory.parseFindSubscribeDtoToSubscribes(any())).thenReturn(list);

        List<SubscribeContentsDto> responseSubscribe = subscribeService.findSubscribe(subscribeFindSubscribeRequestDto);
        assertEquals(list.size(), responseSubscribe.size());
    }

    @Test
    @DisplayName("유저의 아이디를 요청할 시, 유저의 아이디 정보가 테이블에 없다면 WrongIdOrPasswordException 던지며 구독 정보 등록 혹은 수정 실패")
    void 구독조회_로그인_실패처리() {
        SubscribeFindSubscribeRequestDto subscribeFindSubscribeRequestDto = new SubscribeFindSubscribeRequestDto("aaaa");
        when(subscribeFactory.parseFindSubscribeDtoToSubscribes(any())).thenThrow(new WrongIdOrPasswordException());
        assertThrows(WrongIdOrPasswordException.class, () -> subscribeService.findSubscribe(subscribeFindSubscribeRequestDto));
    }
}