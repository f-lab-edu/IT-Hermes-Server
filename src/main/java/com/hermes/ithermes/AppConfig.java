package com.hermes.ithermes;

import com.hermes.ithermes.application.UpdateUserChatId;
import com.hermes.ithermes.infrastructure.UserRepository;
import com.pengrad.telegrambot.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class AppConfig {
    @Value("${telegram-key}")
    private String telegramKey;
    private final UserRepository userRepository;

    @Bean
    public UpdateUserChatId updateUserChatId() {
        UpdateUserChatId updateUserChatId = new UpdateUserChatId(telegramBot());
        updateUserChatId.updateUserChatId(userRepository);
        return updateUserChatId;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot(telegramKey);
    }
}