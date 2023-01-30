package com.hermes.ithermes.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hermes.ithermes.application.UserService;
import com.hermes.ithermes.domain.exception.SameIdException;
import com.hermes.ithermes.domain.exception.SameNicknameException;
import com.hermes.ithermes.domain.exception.UnMatchedPasswordException;
import com.hermes.ithermes.domain.exception.WrongIdOrPasswordException;
import com.hermes.ithermes.domain.util.JobType;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.user.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private UserCreateUserRequestDto userCreateUserRequestDto;


    @Test
    @DisplayName("회원가입_정상처리")
    @WithMockUser
    void 회원가입_정상처리() throws Exception {
        userCreateUserRequestDto = new UserCreateUserRequestDto("test", "test1234!",
                "test1234!", "김승기", JobType.BACKEND, "1", new String[]{"프론트", "백엔드", "인공지능", null, null});

        mockMvc.perform(post("/user/join")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateUserRequestDto)))
                        .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("회원가입_비밀번호_불일치")
    @WithMockUser
    void 회원가입_비밀번호_불일치() throws Exception {
        //Given
        userCreateUserRequestDto = new UserCreateUserRequestDto("test", "test1234",
                "aaaaabbbbb", "김승기", JobType.BACKEND, "1", new String[]{"프론트", "백엔드", "인공지능", null, null});

        when(userService.joinUser(any())).thenThrow(new UnMatchedPasswordException());

        mockMvc.perform(post("/user/join")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateUserRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("로그인_정상처리")
    @WithMockUser
    void 로그인_정상처리() throws Exception {
        //Given
        String id = "kimsk";
        String password = "kimsk1234";
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto(id,password);

        //When
        when(userService.loginUser(any())).thenReturn(new UserLoginResponseDto());

        mockMvc.perform(post("/user/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLoginRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인_실패")
    @WithMockUser
    void 로그인_실패() throws Exception {
        //Given
        String id = "kimsk";
        String password = "kimsk1234";
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto(id,password);

        //When
        when(userService.loginUser(any())).thenThrow(new WrongIdOrPasswordException());

        mockMvc.perform(post("/user/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLoginRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("닉네임수정_정상처리")
    @WithMockUser
    void 닉네임수정_정상처리() throws Exception {
        //Given
        String loginId="test";
        String nickname = "aaaaa";
        UserUpdateNicknameRequestDto userUpdateNicknameRequestDto = new UserUpdateNicknameRequestDto(loginId, nickname);


        when(userService.updateNickname(any())).thenReturn(new CommonResponseDto());

        mockMvc.perform(put("/user/nickname")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateNicknameRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("닉네임수정_실패처리")
    @WithMockUser
    void 닉네임수정_실패처리() throws Exception {
        //Given
        String loginId="test";
        String nickname = "aaaaa";
        UserUpdateNicknameRequestDto userUpdateNicknameRequestDto = new UserUpdateNicknameRequestDto(loginId, nickname);


        when(userService.updateNickname(any())).thenThrow(new SameNicknameException());

        mockMvc.perform(put("/user/nickname")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateNicknameRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("닉네임중복확인_정상처리")
    @WithMockUser
    void 닉네임중복확인_정상처리() throws Exception {
        //Given
        String loginId="test";
        String nickname = "aaaaa";
        UserDuplicateNicknameRequestDto userDuplicateNicknameRequestDto = new UserDuplicateNicknameRequestDto(nickname);
        //아이디도 보낼필요!

        when(userService.checkDuplicateNickname(any())).thenReturn(new CommonResponseDto());

        mockMvc.perform(post("/user/duplicate-nickname")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDuplicateNicknameRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("닉네임중복확인_실패처리")
    @WithMockUser
    void 닉네임중복확인_실패처리() throws Exception {
        //Given
        String loginId="test";
        String nickname = "aaaaa";
        UserDuplicateNicknameRequestDto userDuplicateNicknameRequestDto = new UserDuplicateNicknameRequestDto(nickname);
        //아이디도 보낼필요!

        when(userService.checkDuplicateNickname(any())).thenThrow(new SameNicknameException());

        mockMvc.perform(post("/user/duplicate-nickname")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDuplicateNicknameRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("아이디중복확인_정상처리")
    @WithMockUser
    void 아이디중복확인_정상처리() throws Exception {
        //Given
        String loginId="test";
        UserDuplicateIdRequestDto userDuplicateIdRequestDto = new UserDuplicateIdRequestDto(loginId);
        //아이디도 보낼필요!

        when(userService.checkDuplicateId(any())).thenReturn(new CommonResponseDto());

        mockMvc.perform(post("/user/duplicate-id")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDuplicateIdRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("아이디중복확인_실패처리")
    @WithMockUser
    void 아이디중복확인_실패처리() throws Exception {
        //Given
        String loginId="test";
        UserDuplicateIdRequestDto userDuplicateIdRequestDto = new UserDuplicateIdRequestDto(loginId);

        when(userService.checkDuplicateId(any())).thenThrow(new SameIdException());

        mockMvc.perform(post("/user/duplicate-id")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDuplicateIdRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("아이디삭제_정상처리")
    @WithMockUser
    void 아이디삭제_정상처리() throws Exception {
        //Given
        String loginId="test";
        UserDeleteUserRequestDto userDeleteUserRequestDto  = new UserDeleteUserRequestDto(loginId);
        //아이디도 보낼필요!

        when(userService.deleteUser(any())).thenReturn(new CommonResponseDto());

        mockMvc.perform(put("/user/")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDeleteUserRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("아이디삭제_실패처리")
    @WithMockUser
    void 아이디삭제_실패처리() throws Exception {
        //Given
        String loginId="test";
        UserDeleteUserRequestDto userDeleteUserRequestDto  = new UserDeleteUserRequestDto(loginId);

        when(userService.deleteUser(any())).thenThrow(new SameNicknameException());

        mockMvc.perform(put("/user/")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDeleteUserRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("마이페이지조회_정상처리")
    @WithMockUser
    void 마이페이지조회_정상처리() throws Exception {
        //Given
        String loginId="test";
        UserFindMyDataRequestDto userFindMyDataRequestDto = new UserFindMyDataRequestDto(loginId);

        when(userService.findMyData(any())).thenReturn(new UserFindMyDataResponseDto("test","김승기"));

        mockMvc.perform(post("/user/my-page")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userFindMyDataRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("마이페이지조회_실패처리")
    @WithMockUser
    void 마이페이지조회_실패처리() throws Exception {
        //Given
        String loginId="test";
        UserDuplicateIdRequestDto userDuplicateIdRequestDto = new UserDuplicateIdRequestDto(loginId);

        when(userService.findMyData(any())).thenThrow(new SameIdException());

        mockMvc.perform(post("/user/my-page")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDuplicateIdRequestDto)))
                .andExpect(status().isBadRequest());
    }
}