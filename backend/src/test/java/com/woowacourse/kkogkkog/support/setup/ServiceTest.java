package com.woowacourse.kkogkkog.support.setup;

import com.woowacourse.kkogkkog.infrastructure.application.SlackClient;
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

    @BeforeEach
    void setUp() {
        databaseCleaner.execute();
    }
}
