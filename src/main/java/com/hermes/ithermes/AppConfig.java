package com.hermes.ithermes;

import com.hermes.ithermes.application.UpdateUserChatId;
import com.hermes.ithermes.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final UserRepository userRepository;

    @Bean
    public UpdateUserChatId updateUserChatId(){
        UpdateUserChatId updateUserChatId = new UpdateUserChatId(userRepository);
        //updateUserChatId.updateUserChatId();
        return updateUserChatId;
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}