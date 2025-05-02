package com.sales.user.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedisDebugLogger {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @PostConstruct
    public void logRedisHost() {
        System.out.println(">>> [DEBUG] Redis Host: " + redisHost);
        System.out.println(">>> [DEBUG] Active Spring Profile: " + activeProfile);
    }
}
