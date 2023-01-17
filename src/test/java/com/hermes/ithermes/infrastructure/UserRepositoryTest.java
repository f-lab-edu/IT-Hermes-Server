package com.hermes.ithermes.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("telegramId값이 null이 아닌 유저가 DB상에 1명인지 테스트한다.")
    void findUserTelegramIdIsNotNUll(){
        assertEquals(1,userRepository.findByTelegramIdIsNotNull().size());
    }


}