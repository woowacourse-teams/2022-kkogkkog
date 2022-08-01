package com.woowacourse.kkogkkog.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.kkogkkog.exception.auth.AccessTokenRetrievalFailedException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
    private static final String JWT_USER_ID_TOKEN = "aaaaaa.bbbbbb.cccccc";
    private static final Map<String, String> SLACK_TOKEN_RESPONSE = new HashMap<>() {{
        put("ok", "true");
        put("access_token", "ACCESS_TOKEN");
        put("token_type", "Bearer");
        put("id_token", JWT_USER_ID_TOKEN);
    }};
    private static final Map<String, String> GET_TOKEN_ERROR_RESPONSE = new HashMap<>() {{
        put("error", "invalid_code");
    }};
    private static final Map<String, String> SLACK_USER_INFO_RESPONSE = new HashMap<>() {{
        put("ok", "true");
        put("sub", USER_ID);
        put("https://slack.com/user_id", USER_ID);
        put("https://slack.com/team_id", TEAM_ID);
        put("email", "kkogkkog@gmail.com");
        put("name", "kkogkkog");
        put("picture", "IMAGE_URL");
    }};

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("인증 서버로 코드를 보내 엑세스 토큰을 받아와 정보를 조회한다.")
    void getUserInfoByCode() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();
        setUpResponse(mockWebServer, objectMapper.writeValueAsString(SLACK_TOKEN_RESPONSE));
        setUpResponse(mockWebServer, objectMapper.writeValueAsString(SLACK_USER_INFO_RESPONSE));
        SlackClient slackClient = buildMockSlackClient(mockWebServer);

        SlackUserInfo slackUserInfo = slackClient.getUserInfoByCode("code");
        String userId = slackUserInfo.getUserId();

        assertThat(userId).isEqualTo(USER_ID);
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("요청 과정에서 오류가 발생하면 예외가 발생한다.")
    void getTokenException() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();
        setUpResponse(mockWebServer, objectMapper.writeValueAsString(GET_TOKEN_ERROR_RESPONSE));
        setUpResponse(mockWebServer, objectMapper.writeValueAsString(SLACK_USER_INFO_RESPONSE));
        SlackClient slackClient = buildMockSlackClient(mockWebServer);

        assertThatThrownBy(() -> slackClient.getUserInfoByCode("code"))
            .isInstanceOf(AccessTokenRetrievalFailedException.class);
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
