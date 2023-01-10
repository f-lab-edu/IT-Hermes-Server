package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.exception.SameUserException;
import com.hermes.ithermes.domain.factory.UserFactory;
import com.hermes.ithermes.infrastructure.UserRepository;
import com.hermes.ithermes.presentation.dto.user.UserCreateUserRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserFactory userFactory;

    @Test
    @DisplayName("회원가입 된 정보가 실제 DB에 존재하는 데이터인지 확인")
    void 회원가입_데이터존재유무() {
        //Given
        String id = "testId";
        String password = "test1234";
        String passwordConfirm = "test1234";
        String nickname = "김승기";
        String job = "backend";
        String experience = "1";
        String[] keywordList = {"프론트", "백엔드", "머신러닝", "오픈소스", "빅데이터"};

        //When
        UserCreateUserRequestDto userCreateUserRequestDto = new UserCreateUserRequestDto(id, password, passwordConfirm, nickname, job, experience, keywordList);
        User user = userFactory.parseLoginRequestDtoToUser(userCreateUserRequestDto);
        userRepository.save(user);
        Optional<User> LoginUser = userRepository.findByLoginId(user.getLoginId());

        //Then
        assertTrue(user.getLoginId().equals(LoginUser.get().getLoginId()));
    }

    @Test
    @DisplayName("동일한 회원가입 정보 요청 시, 예외발생")
    void 회원가입_중복체크() {
        //Given
        String id = "testId";
        String password = "test1234";
        String passwordConfirm = "test1234";
        String nickname = "김승기";
        String job = "backend";
        String experience = "1";
        String[] keywordList = {"프론트", "백엔드", "머신러닝", null, null};

        //When
        UserCreateUserRequestDto userCreateUserRequestDto = new UserCreateUserRequestDto(id, password, passwordConfirm, nickname, job, experience, keywordList);

        //Then
        Assertions.assertThrows(SameUserException.class, () -> {
            userService.joinUser(userCreateUserRequestDto);
            userService.joinUser(userCreateUserRequestDto);
        });
    }
}