package com.woowacourse.kkogkkog.auth.application;

import com.woowacourse.kkogkkog.infrastructure.exception.AccessTokenRetrievalFailedException;
import com.woowacourse.kkogkkog.infrastructure.exception.OAuthUserInfoRequestFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

@Slf4j
@Component
public class GoogleClient {

    private static final String LOGIN_URI = "https://oauth2.googleapis.com/token";
    private static final String LOGIN_USER_INFO_URI = "https://www.googleapis.com/oauth2/v3/userinfo?alt=json";
    private static final String CODE_PARAMETER = "code";
    private static final String CLIENT_ID_PARAMETER = "client_id";
    private static final String CLIENT_SECRET_PARAMETER = "client_secret";
    private static final String REDIRECT_URI_PARAMETER = "redirect_uri";
    private static final String GRANT_TYPE_PARAMETER = "grant_type";

    private final String clientId;
    private final String clientSecret;
    private final WebClient oAuthLoginClient;
    private final WebClient userInfoClient;
    private final String loginRedirectUrl;

    public GoogleClient(@Value("${security.google.client-id}") String clientId,
                        @Value("${security.google.client-secret}") String clientSecret,
                        @Value(LOGIN_URI) String oAuthLoginUri,
                        @Value(LOGIN_USER_INFO_URI) String userInfoUri,
                        @Value("${security.google.redirect.login}") String loginRedirectUrl,
                        WebClient webClient) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.oAuthLoginClient = toWebClient(webClient, oAuthLoginUri);
        this.userInfoClient = toWebClient(webClient, userInfoUri);
        this.loginRedirectUrl = loginRedirectUrl;
    }

    private WebClient toWebClient(WebClient webClient, String baseUrl) {
        return webClient.mutate()
            .baseUrl(baseUrl)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    public String requestAccessToken(String code) {
        try {
            GoogleAccessTokenResponse tokenResponse = oAuthLoginClient
                .post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(tokenRequest(code)))
                .retrieve()
                .bodyToMono(GoogleAccessTokenResponse.class)
                .block();
            return tokenResponse.getAccessToken();
        } catch (WebClientException e) {
            log.error("failed to achieve accessToken from Google", e);
            throw new AccessTokenRetrievalFailedException();
        }
    }

    private MultiValueMap<String, String> tokenRequest(String code) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add(CODE_PARAMETER, code);
        formData.add(CLIENT_ID_PARAMETER, clientId);
        formData.add(CLIENT_SECRET_PARAMETER, clientSecret);
        formData.add(REDIRECT_URI_PARAMETER, loginRedirectUrl);
        formData.add(GRANT_TYPE_PARAMETER, "authorization_code");
        return formData;
    }

    public GoogleUserInfo requestUserInfo(String accessToken) {
        try {
            return userInfoClient
                .get()
                .headers(header -> header.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(GoogleUserInfo.class)
                .blockOptional()
                .orElseThrow(OAuthUserInfoRequestFailedException::new);
        } catch (WebClientException e) {
            throw new OAuthUserInfoRequestFailedException();
        }
    }
}
