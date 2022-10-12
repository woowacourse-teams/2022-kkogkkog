package com.woowacourse.kkogkkog.support.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisStorageCleaner {

    @Autowired
    private RedisTemplate<String, ?> redisTemplate;

    public void execute() {
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        connection.flushDb();
    }
}
