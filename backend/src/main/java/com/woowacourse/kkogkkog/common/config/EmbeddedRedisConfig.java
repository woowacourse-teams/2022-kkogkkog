package com.woowacourse.kkogkkog.common.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

@Slf4j
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
        } catch (RuntimeException e) {
            log.info("{} 포트에 내장 레디스 서버를 구동하는 데 실패하였습니다.", redisPort);
        }
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
}
