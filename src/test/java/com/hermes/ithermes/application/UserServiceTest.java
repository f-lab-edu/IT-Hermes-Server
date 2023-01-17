package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.factory.KeywordFactory;
import com.hermes.ithermes.domain.factory.UserFactory;
import com.hermes.ithermes.domain.factory.UserKeywordRegistryFactory;
import com.hermes.ithermes.domain.util.JobType;
import com.hermes.ithermes.infrastructure.UserKeywordRegistryRepository;
import com.hermes.ithermes.infrastructure.UserRepository;
import com.hermes.ithermes.presentation.dto.user.UserCreateUserRequestDto;
import com.hermes.ithermes.presentation.dto.user.UserFindMyDataRequestDto;
import com.hermes.ithermes.presentation.dto.user.UserFindMyDataResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserKeywordRegistryRepository userKeywordRegistryRepository;
    @Mock
    private UserFactory userFactory;
    @Mock
    private KeywordFactory keywordFactory;
    @Mock
    private UserKeywordRegistryFactory userKeywordRegistryFactory;
    @Mock
    private UserRepository userRepository;

    private UserCreateUserRequestDto userCreateUserRequestDto;

    @BeforeEach
    void setUp() {
        userCreateUserRequestDto = new UserCreateUserRequestDto("test", "test1234",
                "test1234", "김승기", JobType.BACKEND, "1", new String[]{"프론트", "백엔드", "인공지능", null, null});
    }

    @Test
    @DisplayName("회원가입")
    void 회원가입() {
        /** 비밀번호 불일치 시, 로직 */
        when(userService.findMyData(new UserFindMyDataRequestDto("test"))).thenReturn(new UserFindMyDataResponseDto());
    }
}