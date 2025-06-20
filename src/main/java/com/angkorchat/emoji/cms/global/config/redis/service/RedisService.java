package com.angkorchat.emoji.cms.global.config.redis.service;

import com.angkorchat.emoji.cms.global.error.RedisDataInputException;
import com.angkorchat.emoji.cms.global.error.RedisKeyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private static final Logger log = LoggerFactory.getLogger(RedisService.class);

    private final StringRedisTemplate redisTemplate;

    public boolean checkRefreshTokenExist(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }


    public String getRefreshToken(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void saveRefreshToken(String key, String refreshToken, Integer timeoutInSeconds) {
        try {
            log.info("refreshToken : {}", refreshToken);
            log.info("redis Key : {}", key);
            redisTemplate.opsForValue().set(key, refreshToken, timeoutInSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getCause().getMessage());
            throw new RedisDataInputException();
        }
    }

    public void saveRefreshToken(String key, String refreshToken) {
        try {
            redisTemplate.opsForValue().set(key, refreshToken);
        } catch (Exception e) {
            throw new RedisDataInputException();
        }
    }

    public void updateRefreshToken(String key, String refreshToken, Integer timeoutInSeconds) {
        if (!checkRefreshTokenExist(key)) {
            throw new RedisKeyNotFoundException(); // 커스텀 예외
        }
        try {
            redisTemplate.opsForValue().set(key, refreshToken, timeoutInSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Redis update error for key {}: {}", key, e.getMessage());
            throw new RedisDataInputException();
        }
    }

    public void deleteRefreshToken(String key) {
        redisTemplate.delete(key);
    }
}