package com.hermes.ithermes;

import com.hermes.ithermes.application.UpdateUserChatId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class config {

    @Bean
    public UpdateUserChatId updateUserChatId(){
        return new UpdateUserChatId();
    }

}
