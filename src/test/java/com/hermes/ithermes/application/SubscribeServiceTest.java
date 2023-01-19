package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.ContentsProvider;
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
import com.hermes.ithermes.presentation.dto.subscribe.SubscribeFindSubscribeRequestDto;
import com.hermes.ithermes.presentation.dto.subscribe.SubscribeFindSubscribeResponseDto;
import com.hermes.ithermes.presentation.dto.subscribe.SubscribePutSubscribeRequestDto;
import com.hermes.ithermes.presentation.dto.user.UserCreateUserRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
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

    private UserCreateUserRequestDto userCreateUserRequestDto;
    private Subscribe subscribe;
    private User user;
    private ContentsProvider contentsProvider;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .loginId("test").nickname("김승기")
                .password("test1234").job(JobType.BACKEND)
                .yearOfExperience(1)
                .build();
        contentsProvider = ContentsProvider.builder()
                .category(CategoryType.JOB)
                .name(ContentsProviderType.SARAMIN)
                .build();

        subscribe = Subscribe.builder()
                .user(user)
                .contentsProvider(contentsProvider)
                .minYearOfExperience(3)
                .maxYearOfExperience(5)
                .job(JobType.BACKEND)
                .isActive(ActiveType.ACTIVE)
                .build();
    }

    @Test
    @DisplayName("회원가입_정상처리")
    void 구독_PUT_정상처리() {
        SubscribePutSubscribeRequestDto subscribePutSubscribeRequestDto = new SubscribePutSubscribeRequestDto("test",
                new String[]{"ACTIVE", "ACTIVE", "NOT_ACTIVE", "NOT_ACTIVE", "NOT_ACTIVE", "NOT_ACTIVE", "NOT_ACTIVE"},
                JobType.BACKEND,
                "3",
                "5");
        assertEquals(new CommonResponseDto().getMessage(), subscribeService.putSubscribe(subscribePutSubscribeRequestDto).getMessage());
    }

    @Test
    @DisplayName("구독_PUT_실패처리")
    void 구독_PUT_실패처리() {
        SubscribePutSubscribeRequestDto subscribePutSubscribeRequestDto = new SubscribePutSubscribeRequestDto("test",
                new String[]{"ACTIVE", "ACTIVE", "NOT_ACTIVE", "NOT_ACTIVE", "NOT_ACTIVE", "NOT_ACTIVE", "NOT_ACTIVE"},
                JobType.BACKEND,
                "3",
                "5");

        when(subscribeFactory.parsePutSubscribeDtoToSubscribes(any())).thenThrow(new WrongIdOrPasswordException());

        assertThrows(WrongIdOrPasswordException.class, () -> subscribeService.putSubscribe(subscribePutSubscribeRequestDto).getMessage());
    }

    @Test
    @DisplayName("구독조회_정상처리")
    void 구독조회_정상처리() {
        SubscribeFindSubscribeRequestDto subscribeFindSubscribeRequestDto = new SubscribeFindSubscribeRequestDto("test");
        when(subscribeFactory.findJobCategoryData(any())).thenReturn(subscribe);
        List<String> list = Arrays.asList("ACTIVE", "ACTIVE", "NOT_ACTIVE", "NOT_ACTIVE", "NOT_ACTIVE", "NOT_ACTIVE", "NOT_ACTIVE");
        when(subscribeFactory.findActiveContentsProviderType(any()))
                .thenReturn(list);

        SubscribeFindSubscribeResponseDto subscribe = subscribeService.findSubscribe(subscribeFindSubscribeRequestDto);
        assertEquals(JobType.BACKEND.name(), subscribe.getJob());
        assertEquals("3", subscribe.getMinYearOfExperience());
        assertEquals("5", subscribe.getMaxYearOfExperience());
        for (int i = 0; i < list.size(); i++) {
            assertEquals(list.get(i), subscribe.getKeywordList().get(i));
        }
    }

    @Test
    @DisplayName("구독조회_실패처리")
    void 구독조회_실패처리() {
        SubscribeFindSubscribeRequestDto subscribeFindSubscribeRequestDto = new SubscribeFindSubscribeRequestDto("test");
        when(subscribeFactory.parseFindSubscribeDtoToSubscribes(any())).thenThrow(new WrongIdOrPasswordException());
        assertThrows(WrongIdOrPasswordException.class, () -> subscribeService.findSubscribe(subscribeFindSubscribeRequestDto));
    }
}