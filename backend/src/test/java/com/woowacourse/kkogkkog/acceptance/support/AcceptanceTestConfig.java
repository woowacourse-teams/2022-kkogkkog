package com.woowacourse.kkogkkog.acceptance.support;

import com.woowacourse.kkogkkog.infrastructure.domain.WoowacourseUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AcceptanceTestConfig {

    @Bean
    @Primary
    public WoowacourseUserRepository woowacourseUserRepository() {
        return new WoowacourseUserRepositoryTestImpl();
    }
}
