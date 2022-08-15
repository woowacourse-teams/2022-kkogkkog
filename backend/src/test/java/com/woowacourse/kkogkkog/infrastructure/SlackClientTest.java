package com.woowacourse.kkogkkog.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.kkogkkog.exception.auth.AccessTokenRetrievalFailedException;
import com.woowacourse.kkogkkog.exception.infrastructure.BotInstallationFailedException;
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
    private static final String BOT_ACCESS_TOKEN = "xoxb-bot-access-token";
    private static final String MESSAGE = "MESSAGE_HERE";
    private static final Map<String, String> SLACK_TOKEN_RESPONSE = new HashMap<>() {{
        put("ok", "true");
        put("access_token", "xoxp-user-access-token");
        put("token_type", "Bearer");
        put("id_token", JWT_USER_ID_TOKEN);
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
    private static final String BOT_TOKEN_RESPONSE = "{\n"
        + "    \"ok\": true,\n"
        + "    \"token_type\": \"bot\",\n"
        + "    \"access_token\": \"" + BOT_ACCESS_TOKEN + "\",\n"
        + "    \"bot_user_id\": \"U03RM5SCKL6\",\n"
        + "    \"team\": {\n"
        + "        \"id\": \"" + TEAM_ID + "\",\n"
        + "        \"name\": \"워크스페이스명\"\n"
        + "    }\n"
        + "}";
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
    private static final Map<String, String> ERROR_RESPONSE = new HashMap<>() {{
        put("ok", "false");
        put("error", "reason_for_failure");
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
        setUpResponse(mockWebServer, objectMapper.writeValueAsString(ERROR_RESPONSE));
        setUpResponse(mockWebServer, objectMapper.writeValueAsString(SLACK_USER_INFO_RESPONSE));
        SlackClient slackClient = buildMockSlackClient(mockWebServer);

        assertThatThrownBy(() -> slackClient.getUserInfoByCode("invalid_code"))
            .isInstanceOf(AccessTokenRetrievalFailedException.class);
    }

    @Test
    @DisplayName("인증 서버로 코드를 보내 봇의 엑세스 토큰을 받아온다.")
    void requestBotAccessToken() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();

        setUpResponse(mockWebServer, BOT_TOKEN_RESPONSE);
        SlackClient slackClient = buildMockSlackClient(mockWebServer);

        WorkspaceResponse actual = slackClient.requestBotAccessToken("code");
        WorkspaceResponse expected = new WorkspaceResponse(TEAM_ID, "워크스페이스명",
            BOT_ACCESS_TOKEN);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("봇 등록에 실패하면 예외를 던진다.")
    void requestBotAccessToken_fail() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();

        setUpResponse(mockWebServer, objectMapper.writeValueAsString(ERROR_RESPONSE));
        SlackClient slackClient = buildMockSlackClient(mockWebServer);

        assertThatThrownBy(() -> slackClient.requestBotAccessToken("invalid_code"))
            .isInstanceOf(BotInstallationFailedException.class);
    }

    @Test
    @DisplayName("슬랙 서버로 푸쉬 알람 요청을 보낸다.")
    void requestPushAlarm() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();
        setUpResponse(mockWebServer, POST_MESSAGE_RESPONSE);
        SlackClient slackClient = buildMockSlackClient(mockWebServer);

        assertThatNoException().isThrownBy(() ->
            slackClient.requestPushAlarm(BOT_ACCESS_TOKEN, USER_ID, MESSAGE));
    }

    private SlackClient buildMockSlackClient(MockWebServer mockWebServer) {
        String mockWebClientURI = String.format("http://%s:%s",
            mockWebServer.getHostName(), mockWebServer.getPort());
        return new SlackClient("clientId", "secretId", mockWebClientURI, mockWebClientURI,
            mockWebClientURI, mockWebClientURI, WebClient.create());
    }

    private void setUpResponse(MockWebServer mockWebServer, String responseBody) {
        mockWebServer.enqueue(new MockResponse()
            .setBody(responseBody)
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }
}
