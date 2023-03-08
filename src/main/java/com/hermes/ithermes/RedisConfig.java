package com.hermes.ithermes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
@EnableRedisRepositories
public class RedisConfig {
    @Value("${spring.redis.ipAddress}")
    private String ipAddress;

    @Bean
    LettuceConnectionFactory lettuceConnectionFactory() {
        log.info("[lettuce ip 설정 확인]: {}", ipAddress);
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(ipAddress,6379);
        return lettuceConnectionFactory;
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        log.info("[redisTemplate 설정 확인]: {}", ipAddress);
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        return template;
    }
}
