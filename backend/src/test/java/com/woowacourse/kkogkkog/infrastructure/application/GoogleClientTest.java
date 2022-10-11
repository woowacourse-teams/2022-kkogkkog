package com.woowacourse.kkogkkog.infrastructure.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.kkogkkog.infrastructure.dto.GoogleUserDto;
import com.woowacourse.kkogkkog.infrastructure.exception.AccessTokenRetrievalFailedException;
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

class GoogleClientTest {

    private static final String ACCESS_TOKEN = "xoxp-user-access-token";
    private static final String USER_NAME = "Gildong Hong";

    private static final Map<String, String> GOOGLE_ACCESS_TOKEN_RESPONSE = new HashMap<>() {{
        put("access_token", ACCESS_TOKEN);
        put("token_type", "Bearer");
    }};

    private static final Map<String, String> GOOGLE_USER_INFO_RESPONSE = new HashMap<>() {{
        put("family_name", "Hong");
        put("sub", "111111111111");
        put("picture", "https://lh3.googleusercontent.com");
        put("locale", "ko");
        put("email_verified", "true");
        put("given_name", "Gildong");
        put("email", "google@gmail.com");
        put("name", USER_NAME);
    }};

    private static final Map<String, String> ERROR_RESPONSE = new HashMap<>() {{
        put("error", "error_type");
        put("error_description", "reason_for_failure");
    }};

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("인증 서버로 코드를 보내 엑세스 토큰을 받아온다.")
    void requestAccessToken() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();
        setUpResponse(mockWebServer, objectMapper.writeValueAsString(GOOGLE_ACCESS_TOKEN_RESPONSE));
        GoogleClient googleClient = buildMockGoogleClient(mockWebServer);

        String accessToken = googleClient.requestAccessToken("code");

        assertThat(accessToken).isEqualTo(ACCESS_TOKEN);
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("서버로 엑세스 토큰을 보내 유저 정보를 가져온다.")
    void requestUserInfo() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();
        setUpResponse(mockWebServer, objectMapper.writeValueAsString(GOOGLE_USER_INFO_RESPONSE));
        GoogleClient googleClient = buildMockGoogleClient(mockWebServer);

        GoogleUserDto googleUserDto = googleClient.requestUserInfo(ACCESS_TOKEN);
        String userName = googleUserDto.getName();

        assertThat(userName).isEqualTo(USER_NAME);
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("요청 과정에서 오류가 발생하면 예외가 발생한다.")
    void requestAccessTokenException() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();
        setUpError(mockWebServer, objectMapper.writeValueAsString(ERROR_RESPONSE));
        setUpResponse(mockWebServer, objectMapper.writeValueAsString(GOOGLE_USER_INFO_RESPONSE));
        GoogleClient googleClient = buildMockGoogleClient(mockWebServer);

        assertThatThrownBy(() -> googleClient.requestAccessToken("invalid_code"))
            .isInstanceOf(AccessTokenRetrievalFailedException.class);
    }

    private GoogleClient buildMockGoogleClient(MockWebServer mockWebServer) {
        String mockWebClientURI = String.format("http://%s:%s",
            mockWebServer.getHostName(), mockWebServer.getPort());

        return new GoogleClient("clientId", "clientSecret", mockWebClientURI, mockWebClientURI,
            mockWebClientURI, WebClient.create());

    }

    private void setUpError(MockWebServer mockWebServer, String responseBody) {
        MockResponse mockResponse = new MockResponse();
        mockResponse.status("HTTP1.1/ 400 Bad Request");
        mockWebServer.enqueue(mockResponse
            .setBody(responseBody)
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    private void setUpResponse(MockWebServer mockWebServer, String responseBody) {
        mockWebServer.enqueue(new MockResponse()
            .setBody(responseBody)
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }
}
