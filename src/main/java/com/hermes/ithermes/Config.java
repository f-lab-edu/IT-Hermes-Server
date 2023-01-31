package com.hermes.ithermes;

import com.hermes.ithermes.application.UpdateUserChatId;
import com.hermes.ithermes.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class Config {

    private final UserRepository userRepository;

    @Bean
    public UpdateUserChatId updateUserChatId(){
        UpdateUserChatId updateUserChatId = new UpdateUserChatId(userRepository);
        //updateUserChatId.updateUserChatId();
        return updateUserChatId;
    }

}
