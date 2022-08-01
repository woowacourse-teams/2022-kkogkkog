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

    private static final String USER_ID = "U0R7JM";

    private static final String SLACK_TOKEN_RESPONSE = "{\n" +
        "    \"ok\": true,\n" +
        "    \"access_token\": \"ACCESS_TOKEN\",\n" +
        "    \"token_type\": \"Bearer\",\n" +
        "    \"id_token\": \"ID_TOKEN\"\n" +
        "}";

    private static final String SLACK_USER_INFO_RESPONSE = "{\n" +
        "    \"ok\": true,\n" +
        "    \"sub\": \"" + USER_ID + "\",\n" +
        "    \"https://slack.com/user_id\": \"U0R7JM\",\n" +
        "    \"https://slack.com/team_id\": \"T0R7GR\",\n" +
        "    \"email\": \"kkogkkog@gmail.com\",\n" +
        "    \"name\": \"krane\",\n" +
        "    \"picture\": \"IMAGE_URL\"\n" +
        "}";

    @Test
    @DisplayName("인증 서버로 코드를 보내 엑세스 토큰을 받아와 정보를 조회한다.")
    void getUserInfoByCode() throws IOException {
            MockWebServer mockWebServer = new MockWebServer();
            mockWebServer.start();

            setUpResponse(mockWebServer, SLACK_TOKEN_RESPONSE);
            setUpResponse(mockWebServer, SLACK_USER_INFO_RESPONSE);

            SlackClient slackClient = new SlackClient(
                "clientId",
                "secretId",
                String.format("http://%s:%s", mockWebServer.getHostName(), mockWebServer.getPort()),
                String.format("http://%s:%s", mockWebServer.getHostName(), mockWebServer.getPort()),
                WebClient.create()
            );

            SlackUserInfo slackUserInfo = slackClient.getUserInfoByCode("code");
            String userId = slackUserInfo.getUserId();

            assertThat(userId).isEqualTo(USER_ID);
            mockWebServer.shutdown();
    }

    @Test
    @DisplayName("요청 과정에서 오류가 발생하면 ErrorResponseToGetAccessTokenException을 응답한다.")
    void getTokenException() {
        String getTokenErrorResponse = "{\n" +
            "    \"error\": \"invalid_code\"\n" +
            "}";

        try (MockWebServer mockWebServer = new MockWebServer()) {
            mockWebServer.start();

            setUpResponse(mockWebServer, getTokenErrorResponse);
            setUpResponse(mockWebServer, SLACK_USER_INFO_RESPONSE);

            SlackClient slackClient = new SlackClient(
                "clientId",
                "secretId",
                String.format("http://%s:%s", mockWebServer.getHostName(), mockWebServer.getPort()),
                String.format("http://%s:%s", mockWebServer.getHostName(), mockWebServer.getPort()),
                WebClient.create()
            );

            assertThatThrownBy(() -> slackClient.getUserInfoByCode("code"))
                .isInstanceOf(ErrorResponseToGetAccessTokenException.class);
        } catch (IOException ignored) {
        }
    }

    private void setUpResponse(MockWebServer mockWebServer, String responseBody) {
        mockWebServer.enqueue(new MockResponse()
            .setBody(responseBody)
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }
}
