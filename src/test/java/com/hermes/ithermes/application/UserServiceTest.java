package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Keyword;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.entity.UserKeywordRegistry;
import com.hermes.ithermes.domain.exception.*;
import com.hermes.ithermes.domain.factory.KeywordFactory;
import com.hermes.ithermes.domain.factory.UserFactory;
import com.hermes.ithermes.domain.factory.UserKeywordRegistryFactory;
import com.hermes.ithermes.domain.util.JobType;
import com.hermes.ithermes.infrastructure.UserKeywordRegistryRepository;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.user.UserCreateUserRequestDto;
import com.hermes.ithermes.presentation.dto.user.UserFindMyDataRequestDto;
import com.hermes.ithermes.presentation.dto.user.UserLoginRequestDto;
import com.hermes.ithermes.presentation.dto.user.UserUpdateNicknameRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserKeywordRegistryRepository userKeywordRegistryRepository;
    @Mock
    private UserKeywordRegistryFactory userKeywordRegistryFactory;
    @Mock
    private UserFactory userFactory;
    @Mock
    private KeywordFactory keywordFactory;

    private UserCreateUserRequestDto userCreateUserRequestDto;
    private User user;
    private UserKeywordRegistry userKeywordRegistry;
    private Keyword keyword;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .loginId("test").nickname("김승기")
                .password("test1234!").job(JobType.BACKEND)
                .yearOfExperience(1)
                .build();

        keyword = Keyword.builder().name("머신러닝").build();

        userKeywordRegistry = UserKeywordRegistry.builder()
                .user(user)
                .keyword(keyword)
                .build();
    }

    @Test
    @DisplayName("회원가입 시 회원정보, 키워드가 테이블에 저장되어 회원가입 성공")
    void 회원가입_정상처리() {
        userCreateUserRequestDto = new UserCreateUserRequestDto("test", "test1234!",
                "test1234!", "김승기", JobType.BACKEND, "1", new String[]{"프론트", "백엔드", "인공지능", null, null});

        when(userFactory.existsByLoginId(any())).thenReturn(false);
        when(userFactory.parseLoginRequestDtoToUser(any())).thenReturn(user);
        when(userKeywordRegistryFactory.parseUserAndKeyword(any(),any())).thenReturn(userKeywordRegistry);

        assertEquals(new CommonResponseDto().getMessage(), userService.joinUser(userCreateUserRequestDto).getMessage());
    }

    @Test
    @DisplayName("회원가입 시 아이디가 유저테이블에 저장 된 데이터와 일치 할 시 SameIdException 던지며 회원가입 실패")
    void 회원가입_이미존재하는회원() {
        UserCreateUserRequestDto userCreateUserRequestDtoTest = new UserCreateUserRequestDto("test", "1q2w3e4r",
                "1q2w3e4r", "김승기", JobType.BACKEND, "1"
                , new String[]{"프론트", "백엔드", "인공지능", "빅데이터", "머신러닝"});

        when(userFactory.existsByLoginId(any())).thenReturn(true);

        assertThrows(SameIdException.class, () -> userService.joinUser(userCreateUserRequestDtoTest));

    }

    @Test
    @DisplayName("회원가입 시 입력한 비밀번호와 비밀번호확인 데이터가 다를 경우 UnMatchedPasswordException 던지며 회원가입 실패")
    void 회원가입_비밀번호불일치() {
        UserCreateUserRequestDto userCreateUserRequestDtoTest = new UserCreateUserRequestDto("test", "1q2w3e4r",
                "1q2w3e4r!!", "김승기", JobType.BACKEND, "1"
                , new String[]{"프론트", "백엔드", "인공지능", "빅데이터", "머신러닝"});

        assertThrows(UnMatchedPasswordException.class, () -> userService.joinUser(userCreateUserRequestDtoTest));
    }

    @Test
    @DisplayName("유저 테이블에 존재하지 하는 아이디 패스워드를 입력하는 경우 로그인 성공")
    void 로그인_정상처리() {
        //Given
        String id = "kimsk";
        String password = "kimsk1234";
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto(id, password);

        //When
        when(userFactory.existsByLoginIdAndPassword(any(), any())).thenReturn(true);

        //Then
        assertEquals(new CommonResponseDto().getMessage(), userService.loginUser(userLoginRequestDto).getMessage());
    }

    @Test
    @DisplayName("유저 테이블에 존재하지 않은 아이디 패스워드를 입력하는 경우 WrongIdOrPasswordException 던지며 로그인 실패")
    void 로그인_회원정보불일치() {
        //Given
        String id = "kimsk";
        String password = "kimsk1234";
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto(id, password);

        //When
        when(userFactory.existsByLoginIdAndPassword(any(), any())).thenReturn(false);

        //Then
        assertThrows(WrongIdOrPasswordException.class, () -> userService.loginUser(userLoginRequestDto));
    }

    @Test
    @DisplayName("유저테이블에 입력한 닉네임이 존재하지 않을 경우 닉네임 수정 성공")
    void 닉네임수정_정상처리() {
        //Given
        String loginId = "kimsk";
        String nickname = "kimsk123";

        UserUpdateNicknameRequestDto userUpdateNicknameRequestDto = new UserUpdateNicknameRequestDto(loginId, nickname);

        when(userFactory.existsByNickname(any())).thenReturn(false);
        when(userFactory.findLoginId(any())).thenReturn(Optional.of(user));
        //Then
        assertEquals(new CommonResponseDto().getMessage(), userService.updateNickname(userUpdateNicknameRequestDto).getMessage());
    }

    @Test
    @DisplayName("유저테이블에 입력한 닉네임이 존재할 경우 SameNicknameException 던지며 닉네임 수정 실패")
    void 닉네임수정_닉네임중복() {
        //Given
        String loginId = "test";
        String nickname = "김승기";

        //When
        UserUpdateNicknameRequestDto userUpdateNicknameRequestDto = new UserUpdateNicknameRequestDto(loginId, nickname);
        when(userFactory.existsByNickname(nickname)).thenReturn(true);

        //Then
        assertThrows(SameNicknameException.class, () -> userService.updateNickname(userUpdateNicknameRequestDto));
    }

    @Test
    @DisplayName("유저 테이블에 존재하는 아이디를 입력했을 경우 유저 데이터 조회 성공")
    void 마이페이지_정상처리() {
        //Given
        String loginId = "test";
        UserFindMyDataRequestDto userFindMyDataRequestDto = new UserFindMyDataRequestDto(loginId);

        //When
        when(userFactory.findLoginId(any())).thenReturn(Optional.of(user));

        //Then
        assertEquals(loginId, userService.findMyData(userFindMyDataRequestDto).getId());
        assertEquals("김승기", userService.findMyData(userFindMyDataRequestDto).getNickname());

    }

    @Test
    @DisplayName("유저 테이블에 존재하지 않는 아이디를 입력했을 경우 WrongIdOrPasswordException 던지며 유저 데이터 조회 실패")
    void 마이페이지_존재하지않는회원() {
        //Given
        String loginId = "dfjerioghjeruiojgheruih";
        UserFindMyDataRequestDto userFindMyDataRequestDto = new UserFindMyDataRequestDto(loginId);

        //When
        when(userFactory.findLoginId(any())).thenReturn(Optional.ofNullable(null));

        //Then
        assertThrows(WrongIdOrPasswordException.class, () -> userService.findMyData(userFindMyDataRequestDto));
    }
}