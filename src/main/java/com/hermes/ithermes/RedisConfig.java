package com.hermes.ithermes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@Slf4j
public class RedisConfig {
    @Value("${spring.redis.ipAddress}")
    private String ipAddress;

    JedisConnectionFactory jedisConnectionFactory() {
        log.info("[Redis IP Address Check] : {}", ipAddress);
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(ipAddress, 6379);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

}
