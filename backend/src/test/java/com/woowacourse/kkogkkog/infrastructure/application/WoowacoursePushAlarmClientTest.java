package com.woowacourse.kkogkkog.infrastructure.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import com.woowacourse.kkogkkog.infrastructure.dto.WoowacourseUsersResponse;
import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
        WoowacoursePushAlarmClient pushAlarmClient = buildMockClient(mockWebServer);

        assertThatNoException().isThrownBy(() ->
            pushAlarmClient.requestPushAlarm(USER_ID, MESSAGE));
    }

    @Test
    @DisplayName("Woowacourse 워크스페이스에 존재하지 않는 사용자이면 404 상태코드를 반환한다.")
    void requestPushAlarm_not_found() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();
        setUpResponse(mockWebServer, HttpStatus.NOT_FOUND);
        WoowacoursePushAlarmClient pushAlarmClient = buildMockClient(mockWebServer);

        assertThatNoException().isThrownBy(() ->
            pushAlarmClient.requestPushAlarm(USER_ID, MESSAGE));
    }

    @Test
    @DisplayName("알림 서버에 장애가 발생하면 500 상태코드를 반환한다.")
    void requestPushAlarm_bad_request() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();
        setUpResponse(mockWebServer, HttpStatus.INTERNAL_SERVER_ERROR);
        WoowacoursePushAlarmClient pushAlarmClient = buildMockClient(mockWebServer);

        assertThatNoException().isThrownBy(() ->
            pushAlarmClient.requestPushAlarm(USER_ID, MESSAGE));
    }

    @Test
    @DisplayName("우아한테크코스 통합 알림봇에서 전체 사용자 목록을 조회한다")
    void requestUsers() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();
        setUpResponseWithUser(mockWebServer, HttpStatus.OK);
        WoowacoursePushAlarmClient pushAlarmClient = buildMockClient(mockWebServer);

        WoowacourseUsersResponse woowacourseUsersResponse = pushAlarmClient.requestUsers();
        assertThat(woowacourseUsersResponse.getMembers().size()).isEqualTo(3);
    }

    private WoowacoursePushAlarmClient buildMockClient(MockWebServer mockWebServer) {
        String mockWebClientURI = String.format("http://%s:%s",
            mockWebServer.getHostName(), mockWebServer.getPort());
        return new WoowacoursePushAlarmClient(REQUEST_PUSH_ALARM_ACCESS_TOKEN, mockWebClientURI,
            WebClient.create());
    }

    private void setUpResponseWithUser(MockWebServer mockWebServer, HttpStatus statusCode) {
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(statusCode.value())
            .setHeader("Content-type", MediaType.APPLICATION_JSON)
            .setBody("{\n"
                + "  \"members\": [\n"
                + "    {\n"
                + "      \"id\": \"USLACKBOT\",\n"
                + "      \"profile\": {\n"
                + "        \"email\": null\n"
                + "      }\n"
                + "    },\n"
                + "    {\n"
                + "      \"id\": \"U03U987DB0W\",\n"
                + "      \"profile\": {\n"
                + "        \"email\": \"heonga2@gmail.com\"\n"
                + "      }\n"
                + "    },\n"
                + "    {\n"
                + "      \"id\": \"U03V5H72L1W\",\n"
                + "      \"profile\": {\n"
                + "        \"email\": \"jinyoungchoi95@gmail.com\"\n"
                + "      }\n"
                + "    }\n"
                + "  ]\n"
                + "}")
        );
    }

    private void setUpResponse(MockWebServer mockWebServer, HttpStatus statusCode) {
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(statusCode.value()));
    }
}
