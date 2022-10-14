package com.woowacourse.kkogkkog.acceptance.support;

import com.woowacourse.kkogkkog.infrastructure.domain.WoowacourseUserRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public WoowacourseUserRepository woowacourseUserRepository() {
        return new WoowacourseUserRepositoryTestImpl();
    }
}
