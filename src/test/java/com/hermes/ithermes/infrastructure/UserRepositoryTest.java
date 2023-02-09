package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.util.JobType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("telegramId값이 null이 아닌 유저가 1명인지 테스트")
    void notExistsTelegramIdUserTest(){
        userRepository.deleteAll();

        User user1 = new User(1l,"회원1","login1","1234", JobType.BACKEND,1,"telegram아이디",false,"eysdfetwetwe");
        User user2 = new User(2l,"회원2","login2","5678", JobType.FRONT,0,null,false,"eysdfetwetwe");

        userRepository.save(user1);
        userRepository.save(user2);

        assertEquals(1, userRepository.findByTelegramIdIsNotNull().size());
    }

}