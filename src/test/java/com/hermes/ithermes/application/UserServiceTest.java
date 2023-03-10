package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Keyword;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.entity.UserKeywordRegistry;
import com.hermes.ithermes.domain.exception.SameIdException;
import com.hermes.ithermes.domain.exception.SameNicknameException;
import com.hermes.ithermes.domain.exception.UnMatchedPasswordException;
import com.hermes.ithermes.domain.exception.WrongIdOrPasswordException;
import com.hermes.ithermes.domain.factory.KeywordFactory;
import com.hermes.ithermes.domain.factory.RedisFactory;
import com.hermes.ithermes.domain.factory.UserFactory;
import com.hermes.ithermes.domain.factory.UserKeywordRegistryFactory;
import com.hermes.ithermes.domain.util.JobType;
import com.hermes.ithermes.infrastructure.UserKeywordRegistryRepository;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.user.UserCreateUserRequestDto;
import com.hermes.ithermes.presentation.dto.user.UserFindMyDataRequestDto;
import com.hermes.ithermes.presentation.dto.user.UserLoginRequestDto;
import com.hermes.ithermes.presentation.dto.user.UserUpdateNicknameRequestDto;
import com.hermes.ithermes.presentation.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

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
    @Mock
    private BCryptPasswordEncoder encoder;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private RedisFactory redisFactory;


    private UserCreateUserRequestDto userCreateUserRequestDto;
    private User user;
    private UserKeywordRegistry userKeywordRegistry;
    private Keyword keyword;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .loginId("test").nickname("?????????")
                .password("test1234!").job(JobType.BACKEND)
                .yearOfExperience(1)
                .build();

        keyword = Keyword.builder().name("????????????").build();

        userKeywordRegistry = UserKeywordRegistry.builder()
                .user(user)
                .keyword(keyword)
                .build();
    }

    @Test
    @DisplayName("???????????? ??? ????????????, ???????????? ???????????? ???????????? ???????????? ??????")
    void ????????????_????????????() {
        userCreateUserRequestDto = new UserCreateUserRequestDto("test", "test1234!",
                "test1234!", "?????????", JobType.BACKEND, "1", new String[]{"?????????", "?????????", "????????????", null, null});

        when(userFactory.existsByLoginId(any())).thenReturn(false);
        when(userFactory.parseLoginRequestDtoToUser(any())).thenReturn(user);
        when(userKeywordRegistryFactory.parseUserAndKeyword(any(), any())).thenReturn(userKeywordRegistry);

        assertEquals(new CommonResponseDto().getMessage(), userService.joinUser(userCreateUserRequestDto).getMessage());
    }

    @Test
    @DisplayName("???????????? ??? ???????????? ?????????????????? ?????? ??? ???????????? ?????? ??? ??? SameIdException ????????? ???????????? ??????")
    void ????????????_????????????????????????() {
        UserCreateUserRequestDto userCreateUserRequestDtoTest = new UserCreateUserRequestDto("test", "1q2w3e4r",
                "1q2w3e4r", "?????????", JobType.BACKEND, "1"
                , new String[]{"?????????", "?????????", "????????????", "????????????", "????????????"});

        when(userFactory.existsByLoginId(any())).thenReturn(true);

        assertThrows(SameIdException.class, () -> userService.joinUser(userCreateUserRequestDtoTest));

    }

    @Test
    @DisplayName("???????????? ??? ????????? ??????????????? ?????????????????? ???????????? ?????? ?????? UnMatchedPasswordException ????????? ???????????? ??????")
    void ????????????_?????????????????????() {
        UserCreateUserRequestDto userCreateUserRequestDtoTest = new UserCreateUserRequestDto("test", "1q2w3e4r",
                "1q2w3e4r!!", "?????????", JobType.BACKEND, "1"
                , new String[]{"?????????", "?????????", "????????????", "????????????", "????????????"});

        assertThrows(UnMatchedPasswordException.class, () -> userService.joinUser(userCreateUserRequestDtoTest));
    }

    @Test
    @DisplayName("?????? ???????????? ???????????? ?????? ????????? ??????????????? ???????????? ?????? ????????? ??????")
    void ?????????_????????????() {
        //Given
        String id = "kimsk";
        String password = "kimsk1234";
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto(id, password);

        //When
        when(userFactory.findLoginId(any())).thenReturn(Optional.ofNullable(user));
        when(jwtUtil.createAccessToken(any())).thenReturn("1q2w3e4r!");
        when(jwtUtil.createRefreshToken(any())).thenReturn("1q2w3e4r!");
        when(encoder.matches(any(), any())).thenReturn(true);
        when(redisFactory.setRedisRefreshToken(any(),any())).thenReturn("1q2w3e4r!");
        //Then
        assertEquals("success", userService.loginUser(userLoginRequestDto).getMessage());
    }

    @Test
    @DisplayName("?????? ???????????? ???????????? ?????? ????????? ??????????????? ???????????? ?????? WrongIdOrPasswordException ????????? ????????? ??????")
    void ?????????_?????????????????????() {
        //Given
        String id = "kimsk";
        String password = "kimsk1234";
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto(id, password);

        //When
        when(userFactory.findLoginId(any())).thenThrow(new WrongIdOrPasswordException());

        //Then
        assertThrows(WrongIdOrPasswordException.class, () -> userService.loginUser(userLoginRequestDto));
    }

    @Test
    @DisplayName("?????????????????? ????????? ???????????? ???????????? ?????? ?????? ????????? ?????? ??????")
    void ???????????????_????????????() {
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
    @DisplayName("?????????????????? ????????? ???????????? ????????? ?????? SameNicknameException ????????? ????????? ?????? ??????")
    void ???????????????_???????????????() {
        //Given
        String loginId = "test";
        String nickname = "?????????";

        //When
        UserUpdateNicknameRequestDto userUpdateNicknameRequestDto = new UserUpdateNicknameRequestDto(loginId, nickname);
        when(userFactory.existsByNickname(nickname)).thenReturn(true);

        //Then
        assertThrows(SameNicknameException.class, () -> userService.updateNickname(userUpdateNicknameRequestDto));
    }

    @Test
    @DisplayName("?????? ???????????? ???????????? ???????????? ???????????? ?????? ?????? ????????? ?????? ??????")
    void ???????????????_????????????() {
        //Given
        String loginId = "test";
        UserFindMyDataRequestDto userFindMyDataRequestDto = new UserFindMyDataRequestDto(loginId);

        //When
        when(userFactory.findLoginId(any())).thenReturn(Optional.of(user));

        //Then
        assertEquals(loginId, userService.findMyData(userFindMyDataRequestDto).getId());
        assertEquals("?????????", userService.findMyData(userFindMyDataRequestDto).getNickname());

    }

    @Test
    @DisplayName("?????? ???????????? ???????????? ?????? ???????????? ???????????? ?????? WrongIdOrPasswordException ????????? ?????? ????????? ?????? ??????")
    void ???????????????_????????????????????????() {
        //Given
        String loginId = "dfjerioghjeruiojgheruih";
        UserFindMyDataRequestDto userFindMyDataRequestDto = new UserFindMyDataRequestDto(loginId);

        //When
        when(userFactory.findLoginId(any())).thenReturn(Optional.ofNullable(null));

        //Then
        assertThrows(WrongIdOrPasswordException.class, () -> userService.findMyData(userFindMyDataRequestDto));
    }
}