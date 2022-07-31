package com.woowacourse.kkogkkog.infrastructure;

import com.woowacourse.kkogkkog.exception.auth.ErrorResponseToGetAccessTokenException;
import com.woowacourse.kkogkkog.exception.auth.UnableToGetTokenResponseException;
import com.woowacourse.kkogkkog.exception.auth.UnableToGetUserInfoResponseException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

@Component
public class SlackRequester {

    private static final String OAUTH_LOGIN_URI = "https://slack.com/api/openid.connect.token";
    private static final String OAUTH_USER_INFO = "https://slack.com/api/openid.connect.userInfo";
    private static final ParameterizedTypeReference<Map<String, Object>> PARAMETERIZED_TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };

    private final String clientId;
    private final String secretId;
    private final WebClient oAuthLoginClient;
    private final WebClient userClient;

    public SlackRequester(
        @Value("${security.slack.client-id}") String clientId,
        @Value("${security.slack.secret-id}") String secretId,
        @Value(OAUTH_LOGIN_URI) String oAuthLoginUri,
        @Value(OAUTH_USER_INFO) String userInfoUri,
        WebClient webClient) {
        this.clientId = clientId;
        this.secretId = secretId;
        this.oAuthLoginClient = toWebClient(webClient, oAuthLoginUri);
        this.userClient = toWebClient(webClient, userInfoUri);
    }

    private WebClient toWebClient(WebClient webClient, String baseUrl) {
        return webClient.mutate()
            .baseUrl(baseUrl)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    public SlackUserInfo getUserInfoByCode(final String code) {
        String token = requestAccessToken(code);
        return requestUserInfo(token);
    }

    private String requestAccessToken(String code) {
        Map<String, Object> responseBody = oAuthLoginClient
            .post()
            .uri(uriBuilder -> toRequestTokenUri(code, uriBuilder))
            .headers(this::setHeaders)
            .retrieve()
            .bodyToMono(PARAMETERIZED_TYPE_REFERENCE)
            .blockOptional()
            .orElseThrow(UnableToGetTokenResponseException::new);
        validateResponseBody(responseBody);

        return responseBody.get("access_token").toString();
    }

    private URI toRequestTokenUri(String code, UriBuilder uriBuilder) {
        return uriBuilder
            .queryParam("code", code)
            .queryParam("client_id", clientId)
            .queryParam("client_secret", secretId)
            .build();
    }

    private void setHeaders(HttpHeaders header) {
        header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
    }

    private void validateResponseBody(Map<String, Object> responseBody) {
        if (!responseBody.containsKey("access_token")) {
            throw new ErrorResponseToGetAccessTokenException(responseBody.get("error").toString());
        }
    }

    private SlackUserInfo requestUserInfo(String token) {
        SlackUserInfo slackUserInfoResponse = userClient
            .get()
            .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
            .retrieve()
            .bodyToMono(SlackUserInfo.class)
            .blockOptional()
            .orElseThrow(UnableToGetUserInfoResponseException::new);

        return slackUserInfoResponse;
    }
}
