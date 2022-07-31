package com.woowacourse.kkogkkog.application;

import com.woowacourse.kkogkkog.infrastructure.SlackRequester;
import com.woowacourse.kkogkkog.support.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ServiceTest {

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @MockBean
    protected SlackRequester slackRequester;

    @BeforeEach
    void setUp() {
        databaseCleaner.execute();
    }
}
