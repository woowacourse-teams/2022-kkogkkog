package com.woowacourse.kkogkkog.support.application;

import com.woowacourse.kkogkkog.auth.application.GoogleClient;
import com.woowacourse.kkogkkog.infrastructure.application.SlackClient;
import com.woowacourse.kkogkkog.support.common.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ServiceTest {

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @MockBean
    protected SlackClient slackClient;

    @MockBean
    protected GoogleClient googleClient;

    @BeforeEach
    void setUp() {
        databaseCleaner.execute();
    }
}
