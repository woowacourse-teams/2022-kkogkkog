package com.woowacourse.kkogkkog.infrastructure;

import com.woowacourse.kkogkkog.exception.auth.ErrorResponseToGetAccessTokenException;
import com.woowacourse.kkogkkog.exception.auth.UnableToGetTokenResponseException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class SlackRequester {

    private static final String OAUTH_LOGIN_URI = "https://slack.com/api/openid.connect.token";
    private static final String OAUTH_USER_INFO = "https://slack.com/api/openid.connect.userInfo";

    private final String clientId;
    private final String secretId;
    private final WebClient oAuthLoginClient;
    private final WebClient userClient;

    public SlackRequester(@Value("${security.slack.client-id}") String clientId,
                          @Value("${security.slack.secret-id}") String secretId,
                          WebClient webClient) {
        this.clientId = clientId;
        this.secretId = secretId;
        this.oAuthLoginClient = oAuthLoginClient(webClient);
        this.userClient = userClient(webClient);
    }

    public SlackUserInfo getUserInfoByCode(final String code) {
        String token = getToken(code);
        return getUserInfo(token);
    }

    private String getToken(String code) {
        Map<String, Object> responseBody = oAuthLoginClient
            .post()
            .uri(uriBuilder -> uriBuilder
                .queryParam("code", code)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", secretId)
                .build())
            .headers(header -> {
                header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
            })
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
            })
            .blockOptional()
            .orElseThrow(UnableToGetTokenResponseException::new);
        validateResponseBody(responseBody);

        return responseBody.get("access_token").toString();
    }

    private SlackUserInfo getUserInfo(String token) {
        SlackUserInfo slackUserInfoResponse = userClient
            .get()
            .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
            .retrieve()
            .bodyToMono(SlackUserInfo.class)
            .blockOptional()
            .orElseThrow(UnableToGetTokenResponseException::new);

        return slackUserInfoResponse;
    }

    private void validateResponseBody(Map<String, Object> responseBody) {
        if (!responseBody.containsKey("access_token")) {
            throw new ErrorResponseToGetAccessTokenException(responseBody.get("error").toString());
        }
    }

    private WebClient oAuthLoginClient(WebClient webClient) {
        return webClient.mutate()
            .baseUrl(OAUTH_LOGIN_URI)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    private WebClient userClient(WebClient webClient) {
        return webClient.mutate()
            .baseUrl(OAUTH_USER_INFO)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }
}
