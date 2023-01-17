package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Keyword;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.entity.UserKeywordRegistry;
import com.hermes.ithermes.domain.exception.SameNicknameException;
import com.hermes.ithermes.domain.exception.SameUserException;
import com.hermes.ithermes.domain.exception.UnMatchedPasswordException;
import com.hermes.ithermes.domain.exception.WrongIdOrPasswordException;
import com.hermes.ithermes.domain.factory.KeywordFactory;
import com.hermes.ithermes.domain.factory.UserFactory;
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
    private UserFactory userFactory;
    @Mock
    private KeywordFactory keywordFactory;

    private UserCreateUserRequestDto userCreateUserRequestDto;
    private User user;

    @BeforeEach
    void setUp() {
        userCreateUserRequestDto = new UserCreateUserRequestDto("test", "test1234",
                "test1234", "김승기", JobType.BACKEND, "1", new String[]{"프론트", "백엔드", "인공지능", null, null});
        user = User.builder()
                .loginId("test").nickname("김승기")
                .password("test1234").job(JobType.BACKEND)
                .yearOfExperience(1)
                .build();
        List<UserKeywordRegistry> userKeywordRegistries = new ArrayList<>();

        Arrays.stream(userCreateUserRequestDto.getKeywordList()).filter(v -> Objects.nonNull(v))
                .forEach(v-> {
                    Keyword keyword = keywordFactory.parseKeywordNameToKeyword(v);
                    UserKeywordRegistry userKeywordRegistry = UserKeywordRegistry.builder()
                            .keyword(keyword).user(user).build();
                    userKeywordRegistries.add(userKeywordRegistry);
        });
    }

    @Test
    @DisplayName("회원가입_정상처리")
    void 회원가입_정상처리() {
        assertEquals(new CommonResponseDto().getMessage(), userService.joinUser(userCreateUserRequestDto).getMessage());
    }

    @Test
    @DisplayName("회원가입_이미존재하는회원")
    void 회원가입_이미존재하는회원() {
        UserCreateUserRequestDto userCreateUserRequestDtoTest = new UserCreateUserRequestDto("test", "1q2w3e4r",
                "1q2w3e4r", "김승기", JobType.BACKEND, "1"
                , new String[]{"프론트", "백엔드", "인공지능", "빅데이터", "머신러닝"});

        when(userFactory.findLoginId(userCreateUserRequestDto.getId())).thenReturn(Optional.of(user));

        assertThrows(SameUserException.class, ()->userService.joinUser(userCreateUserRequestDtoTest));
    }

    @Test
    @DisplayName("회원가입_비밀번호불일치")
    void 회원가입_비밀번호불일치() {
        UserCreateUserRequestDto userCreateUserRequestDtoTest = new UserCreateUserRequestDto("test", "1q2w3e4r",
                "1q2w3e4r!!", "김승기", JobType.BACKEND, "1"
                , new String[]{"프론트", "백엔드", "인공지능", "빅데이터", "머신러닝"});

        assertThrows(UnMatchedPasswordException.class, ()->userService.joinUser(userCreateUserRequestDtoTest));
    }

    @Test
    @DisplayName("로그인_정상처리")
    void 로그인_정상처리() {
        //Given
        String id = "kimsk";
        String password = "kimsk1234";
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto(id,password);

        //When
        when(userFactory.findLoginIdAndPassword(any(),any())).thenReturn(Optional.of(user));

        //Then
        assertEquals(new CommonResponseDto().getMessage(), userService.loginUser(userLoginRequestDto).getMessage());
    }

    @Test
    @DisplayName("로그인_회원정보불일치")
    void 로그인_회원정보불일치() {
        //Given
        String id = "kimsk";
        String password = "kimsk1234";
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto(id,password);

        //When
        when(userFactory.findLoginIdAndPassword(any(),any())).thenReturn(Optional.ofNullable(null));

        //Then
        assertThrows(WrongIdOrPasswordException.class, ()->userService.loginUser(userLoginRequestDto));
    }

    @Test
    @DisplayName("닉네임수정_정상처리")
    void 닉네임수정_정상처리() {
        //Given
        String loginId="test";
        String nickname = "aaaaa";
        UserUpdateNicknameRequestDto userUpdateNicknameRequestDto = new UserUpdateNicknameRequestDto(loginId, nickname);

        when(userFactory.findLoginId(userCreateUserRequestDto.getId())).thenReturn(Optional.of(user));

        //Then
        assertEquals(new CommonResponseDto().getMessage(), userService.updateNickname(userUpdateNicknameRequestDto).getMessage());
    }

    @Test
    @DisplayName("닉네임수정_닉네임중복")
    void 닉네임수정_닉네임중복() {
        //Given
        String loginId="test";
        String nickname = "김승기";

        //When
        UserUpdateNicknameRequestDto userUpdateNicknameRequestDto = new UserUpdateNicknameRequestDto(loginId, nickname);
        when(userFactory.findNickname(nickname)).thenReturn(Optional.of(user));

        //Then
        assertThrows(SameNicknameException.class, ()->userService.updateNickname(userUpdateNicknameRequestDto));
    }

    @Test
    @DisplayName("마이페이지_정상처리")
    void 마이페이지_정상처리() {
        //Given
        String loginId="test";
        String nickname="김승기";
        UserFindMyDataRequestDto userFindMyDataRequestDto = new UserFindMyDataRequestDto(loginId);

        //When
        when(userFactory.findLoginId(loginId)).thenReturn(Optional.of(user));

        //Then
        assertEquals(loginId, userService.findMyData(userFindMyDataRequestDto).getId());
        assertEquals(nickname, userService.findMyData(userFindMyDataRequestDto).getNickname());

    }

    @Test
    @DisplayName("마이페이지_존재하지않는회원")
    void 마이페이지_존재하지않는회원() {
        //Given
        String loginId="dfjerioghjeruiojgheruih";
        UserFindMyDataRequestDto userFindMyDataRequestDto = new UserFindMyDataRequestDto(loginId);

        //When
        when(userFactory.findLoginId(loginId)).thenReturn(Optional.ofNullable(null));

        //Then
        assertThrows(WrongIdOrPasswordException.class, ()->userService.findMyData(userFindMyDataRequestDto));
    }
}