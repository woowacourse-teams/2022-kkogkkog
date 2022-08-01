package com.woowacourse.kkogkkog.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.exception.auth.ErrorResponseToGetAccessTokenException;
import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

class SlackClientTest {

    private static final String USER_ID = "ABC123";
    private static final String TEAM_ID = "TEAM12";
    private static final String SLACK_TOKEN_RESPONSE = "{\n" +
        "    \"ok\": true,\n" +
        "    \"access_token\": \"ACCESS_TOKEN\",\n" +
        "    \"token_type\": \"Bearer\",\n" +
        "    \"id_token\": \"ID_TOKEN\"\n" +
        "}";
    private static final String SLACK_USER_INFO_RESPONSE = "{\n" +
        "    \"ok\": true,\n" +
        "    \"sub\": \"" + USER_ID + "\",\n" +
        "    \"https://slack.com/user_id\": \"" + USER_ID + "\",\n" +
        "    \"https://slack.com/team_id\": \"" + TEAM_ID + "\",\n" +
        "    \"email\": \"kkogkkog@gmail.com\",\n" +
        "    \"name\": \"kkogkkog\",\n" +
        "    \"picture\": \"IMAGE_URL\"\n" +
        "}";

    @Test
    @DisplayName("인증 서버로 코드를 보내 엑세스 토큰을 받아와 정보를 조회한다.")
    void getUserInfoByCode() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();
        setUpResponse(mockWebServer, SLACK_TOKEN_RESPONSE);
        setUpResponse(mockWebServer, SLACK_USER_INFO_RESPONSE);
        SlackClient slackClient = buildMockSlackClient(mockWebServer);

        SlackUserInfo slackUserInfo = slackClient.getUserInfoByCode("code");
        String userId = slackUserInfo.getUserId();

        assertThat(userId).isEqualTo(USER_ID);
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("요청 과정에서 오류가 발생하면 예외가 발생한다.")
    void getTokenException() throws IOException {
        String getTokenErrorResponse = "{\n" +
            "    \"error\": \"invalid_code\"\n" +
            "}";
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();
        setUpResponse(mockWebServer, getTokenErrorResponse);
        setUpResponse(mockWebServer, SLACK_USER_INFO_RESPONSE);
        SlackClient slackClient = buildMockSlackClient(mockWebServer);

        assertThatThrownBy(() -> slackClient.getUserInfoByCode("code"))
            .isInstanceOf(ErrorResponseToGetAccessTokenException.class);
    }

    private SlackClient buildMockSlackClient(MockWebServer mockWebServer) {
        String mockWebClientURI = String.format("http://%s:%s",
            mockWebServer.getHostName(), mockWebServer.getPort());
        return new SlackClient("clientId", "secretId",
            mockWebClientURI, mockWebClientURI, WebClient.create());
    }

    private void setUpResponse(MockWebServer mockWebServer, String responseBody) {
        mockWebServer.enqueue(new MockResponse()
            .setBody(responseBody)
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }
}
