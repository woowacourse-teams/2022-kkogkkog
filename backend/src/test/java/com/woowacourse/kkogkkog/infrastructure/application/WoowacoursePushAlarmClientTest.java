package com.woowacourse.kkogkkog.infrastructure.application;

import static org.assertj.core.api.Assertions.assertThatNoException;

import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

class WoowacoursePushAlarmClientTest {

    private static final String USER_ID = "ABC123";
    private static final String REQUEST_PUSH_ALARM_ACCESS_TOKEN = "aaa.bbb.ccc";
    private static final String MESSAGE = "MESSAGE_HERE";

    @Test
    @DisplayName("슬랙 서버로 푸쉬 알람 요청을 보낸다.")
    void requestPushAlarm_success() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();
        setUpResponse(mockWebServer, HttpStatus.OK);
        WoowacoursePushAlarmClient pushAlarmClient = buildMockSlackClient(mockWebServer);

        assertThatNoException().isThrownBy(() ->
            pushAlarmClient.requestPushAlarm(null, USER_ID, MESSAGE));
    }

    @Test
    @DisplayName("Woowacourse 워크스페이스에 존재하지 않는 사용자이면 404 상태코드를 반환한다.")
    void requestPushAlarm_not_found() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();
        setUpResponse(mockWebServer, HttpStatus.NOT_FOUND);
        WoowacoursePushAlarmClient pushAlarmClient = buildMockSlackClient(mockWebServer);

        assertThatNoException().isThrownBy(() ->
            pushAlarmClient.requestPushAlarm(null, USER_ID, MESSAGE));
    }

    @Test
    @DisplayName("알림 서버에 장애가 발생하면 500 상태코드를 반환한다.")
    void requestPushAlarm_bad_request() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();
        setUpResponse(mockWebServer, HttpStatus.INTERNAL_SERVER_ERROR);
        WoowacoursePushAlarmClient pushAlarmClient = buildMockSlackClient(mockWebServer);

        assertThatNoException().isThrownBy(() ->
            pushAlarmClient.requestPushAlarm(null, USER_ID, MESSAGE));
    }

    private WoowacoursePushAlarmClient buildMockSlackClient(MockWebServer mockWebServer) {
        String mockWebClientURI = String.format("http://%s:%s",
            mockWebServer.getHostName(), mockWebServer.getPort());
        return new WoowacoursePushAlarmClient(mockWebClientURI, REQUEST_PUSH_ALARM_ACCESS_TOKEN,
            WebClient.create());
    }

    private void setUpResponse(MockWebServer mockWebServer, HttpStatus statusCode) {
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(statusCode.value()));
    }
}
