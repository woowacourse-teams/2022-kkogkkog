package com.woowacourse.kkogkkog.common.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

@Profile("default")
@Configuration
public class EmbeddedRedisConfig {

    private final int redisPort;

    private RedisServer redisServer;

    public EmbeddedRedisConfig(@Value("${spring.redis.port}") final int redisPort) {
        this.redisPort = redisPort;
    }

    @PostConstruct
    public void redisServer() {
        try {
            redisServer = new RedisServer(redisPort);
            redisServer.start();
        } catch (Exception e) {
        }
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
}
