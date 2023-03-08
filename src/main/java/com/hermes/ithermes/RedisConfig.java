package com.hermes.ithermes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@Slf4j
public class RedisConfig {
    @Value("${spring.redis.ipAddress}")
    private String ipAddress;

    LettuceConnectionFactory lettuceConnectionFactory() {
        log.info("[IP Address Check] : {}", ipAddress);
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(ipAddress, 6379);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory());
        return template;
    }

}
