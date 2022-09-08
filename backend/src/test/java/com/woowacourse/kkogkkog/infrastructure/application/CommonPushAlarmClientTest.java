package com.woowacourse.kkogkkog.infrastructure.application;

import static org.assertj.core.api.Assertions.assertThatNoException;

import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

class CommonPushAlarmClientTest {

    private static final String USER_ID = "ABC123";
    private static final String BOT_ACCESS_TOKEN = "xoxb-bot-access-token";
    private static final String MESSAGE = "MESSAGE_HERE";

    private static final String POST_MESSAGE_RESPONSE = "{\n"
        + "    \"ok\": true,\n"
        + "    \"channel\": \"" + USER_ID + "\",\n"
        + "    \"ts\": \"1503435956.000247\",\n"
        + "    \"message\": {\n"
        + "        \"text\": \"" + MESSAGE + "\",\n"
        + "        \"username\": \"ecto1\",\n"
        + "        \"type\": \"message\"\n"
        + "    }\n"
        + "}";

    @Test
    @DisplayName("슬랙 서버로 푸쉬 알람 요청을 보낸다.")
    void requestPushAlarm() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();
        setUpResponse(mockWebServer, POST_MESSAGE_RESPONSE);
        CommonPushAlarmClient pushAlarmClient = buildMockSlackClient(mockWebServer);

        assertThatNoException().isThrownBy(() ->
            pushAlarmClient.requestPushAlarm(BOT_ACCESS_TOKEN, USER_ID, MESSAGE));
    }

    private CommonPushAlarmClient buildMockSlackClient(MockWebServer mockWebServer) {
        String mockWebClientURI = String.format("http://%s:%s",
            mockWebServer.getHostName(), mockWebServer.getPort());
        return new CommonPushAlarmClient(mockWebClientURI, WebClient.create());
    }

    private void setUpResponse(MockWebServer mockWebServer, String responseBody) {
        mockWebServer.enqueue(new MockResponse()
            .setBody(responseBody)
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }
}
