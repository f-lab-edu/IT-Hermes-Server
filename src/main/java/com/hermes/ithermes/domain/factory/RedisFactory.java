package com.hermes.ithermes.domain.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisFactory {

    private final RedisTemplate<String, String> redisTemplate;

    public String setRedisRefreshToken(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return value;
    }

    public String getRedisRefreshToken(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteRedisRefreshToken(String key) {
        redisTemplate.delete(key);
    }
}
