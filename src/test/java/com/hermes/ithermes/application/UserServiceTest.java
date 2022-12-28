package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.exception.SameUserException;
import com.hermes.ithermes.infrastructure.UserRepository;
import com.hermes.ithermes.presentation.dto.user.UserCreateUserRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원가입 된 정보가 실제 DB에 존재하는 데이터인지 확인")
    void 회원가입_데이터존재유무() {
        //Given
        String id="testId";
        String password="test1234";
        String passwordConfirm="test1234";
        String nickname="김승기";
        String job="백엔드";
        String experience="1";

        //When
        UserCreateUserRequestDto userCreateUserRequestDto = new UserCreateUserRequestDto(id,password,passwordConfirm,nickname,job,experience);
        User user = userCreateUserRequestDto.parseUserToLoginRequest(userCreateUserRequestDto);
        String userId = userRepository.save(user);

        //Then
        assertTrue(userRepository.findByUserId(userId).get(0).equals(user));
    }

    @Test
    @DisplayName("동일한 회원가입 정보 요청 시, 예외발생")
    void 회원가입_중복체크() {
        //Given
        String id="testId";
        String password="test1234";
        String passwordConfirm="test1234";
        String nickname="김승기";
        String job="백엔드";
        String experience="1";

        //When
        UserCreateUserRequestDto userCreateUserRequestDto = new UserCreateUserRequestDto(id,password,passwordConfirm,nickname,job,experience);

        //Then
        Assertions.assertThrows(SameUserException.class, () -> {
            userService.joinUser(userCreateUserRequestDto);
            userService.joinUser(userCreateUserRequestDto);
        });
    }
}