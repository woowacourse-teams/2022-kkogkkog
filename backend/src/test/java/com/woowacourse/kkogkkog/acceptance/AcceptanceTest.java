package com.woowacourse.kkogkkog.acceptance;

import com.woowacourse.kkogkkog.infrastructure.SlackClient;
import com.woowacourse.kkogkkog.support.DatabaseCleaner;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    @LocalServerPort
    protected int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @MockBean
    protected static SlackClient slackClient;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        databaseCleaner.execute();
    }
}
