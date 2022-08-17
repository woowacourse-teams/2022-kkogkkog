package com.woowacourse.kkogkkog.infrastructure.application;

import com.woowacourse.kkogkkog.auth.exception.AccessTokenRequestFailedException;
import com.woowacourse.kkogkkog.infrastructure.dto.PushAlarmRequest;
import com.woowacourse.kkogkkog.infrastructure.exception.AccessTokenRetrievalFailedException;
import com.woowacourse.kkogkkog.infrastructure.exception.OAuthUserInfoRequestFailedException;
import com.woowacourse.kkogkkog.infrastructure.exception.BotInstallationFailedException;
import com.woowacourse.kkogkkog.infrastructure.exception.PostMessageRequestFailedException;
import com.woowacourse.kkogkkog.infrastructure.dto.BotTokenResponse;
import com.woowacourse.kkogkkog.infrastructure.dto.SlackUserInfo;
import com.woowacourse.kkogkkog.infrastructure.dto.WorkspaceResponse;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.util.UriBuilder;

@Slf4j
@Component
public class SlackClient {

    private static final String LOGIN_URI = "https://slack.com/api/openid.connect.token";
    private static final String LOGIN_USER_INFO_URI = "https://slack.com/api/openid.connect.userInfo";
    private static final String BOT_TOKEN_URI = "https://slack.com/api/oauth.v2.access";
    private static final String MESSAGE_URI = "https://slack.com/api/chat.postMessage";
    private static final String CODE_PARAMETER = "code";
    private static final String CLIENT_ID_PARAMETER = "client_id";
    private static final String SECRET_ID_PARAMETER = "client_secret";
    private static final String REDIRECT_URI_PARAMETER = "redirect_uri";
    private static final String USER_ID_PARAMETER = "channel";
    private static final String MESSAGE_PARAMETER = "text";
    private static final ParameterizedTypeReference<Map<String, Object>> PARAMETERIZED_TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };

    private final String clientId;
    private final String secretId;
    private final WebClient oAuthLoginClient;
    private final WebClient userClient;
    private final WebClient botTokenClient;
    private final WebClient messageClient;
    private final String loginRedirectUrl;
    private final String botTokenRedirectUrl;

    public SlackClient(@Value("${security.slack.client-id}") String clientId,
                       @Value("${security.slack.secret-id}") String secretId,
                       @Value(LOGIN_URI) String oAuthLoginUri,
                       @Value(LOGIN_USER_INFO_URI) String userInfoUri,
                       @Value(BOT_TOKEN_URI) String botTokenUri,
                       @Value(MESSAGE_URI) String messageUri,
                       @Value("${security.slack.redirect.login}") String loginRedirectUrl,
                       @Value("${security.slack.redirect.bot-token}") String botTokenRedirectUrl,
                       WebClient webClient) {
        this.clientId = clientId;
        this.secretId = secretId;
        this.oAuthLoginClient = toWebClient(webClient, oAuthLoginUri);
        this.userClient = toWebClient(webClient, userInfoUri);
        this.botTokenClient = toWebClient(webClient, botTokenUri);
        this.messageClient = toWebClient(webClient, messageUri);
        this.loginRedirectUrl = loginRedirectUrl;
        this.botTokenRedirectUrl = botTokenRedirectUrl;
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
            .uri(uriBuilder -> toRequestTokenUri(uriBuilder, code, loginRedirectUrl))
            .headers(this::setHeaders)
            .retrieve()
            .bodyToMono(PARAMETERIZED_TYPE_REFERENCE)
            .blockOptional()
            .orElseThrow(AccessTokenRequestFailedException::new);

        if (!responseBody.containsKey("access_token")) {
            log.info("Error message From Slack : ", responseBody.get("error"));
            throw new AccessTokenRetrievalFailedException();
        }
        return responseBody.get("access_token").toString();
    }

    private SlackUserInfo requestUserInfo(String token) {
        try {
            return userClient
                .get()
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .retrieve()
                .bodyToMono(SlackUserInfo.class)
                .blockOptional()
                .orElseThrow(OAuthUserInfoRequestFailedException::new);
        } catch (WebClientException e) {
            throw new OAuthUserInfoRequestFailedException();
        }
    }

    public WorkspaceResponse requestBotAccessToken(String code) {
        BotTokenResponse botTokenResponse = getBotTokenResponse(code);
        if (!botTokenResponse.getOk()) {
            throw new BotInstallationFailedException();
        }
        return new WorkspaceResponse(botTokenResponse.getTeam().getId(),
            botTokenResponse.getTeam().getName(),
            botTokenResponse.getAccessToken());
    }

    private BotTokenResponse getBotTokenResponse(String code) {
        BotTokenResponse response = botTokenClient
            .post()
            .uri(uriBuilder -> toRequestTokenUri(uriBuilder, code, botTokenRedirectUrl))
            .headers(this::setHeaders)
            .retrieve()
            .bodyToMono(BotTokenResponse.class)
            .blockOptional()
            .orElseThrow(BotInstallationFailedException::new);
        if (!response.getOk()) {
            throw new BotInstallationFailedException(response.getError());
        }
        return response;
    }

    public void requestPushAlarm(String token, String userId, String message) {
        try {
            Map<String, Object> responseBody = messageClient
                .post()
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .bodyValue(PushAlarmRequest.of(userId, message))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PARAMETERIZED_TYPE_REFERENCE)
                .blockOptional()
                .orElseThrow(PostMessageRequestFailedException::new);
            if (responseBody.get("ok").equals("false")) {
                throw new PostMessageRequestFailedException((String) responseBody.get("error"));
            }
        } catch (PostMessageRequestFailedException e) {
            log.info("Exception has been thrown : ", e);
        }
    }

    private URI toRequestTokenUri(UriBuilder uriBuilder, String code, String redirectUri) {
        return uriBuilder
            .queryParam(CODE_PARAMETER, code)
            .queryParam(CLIENT_ID_PARAMETER, clientId)
            .queryParam(SECRET_ID_PARAMETER, secretId)
            .queryParam(REDIRECT_URI_PARAMETER, redirectUri)
            .build();
    }

    private void setHeaders(HttpHeaders header) {
        header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
    }
}
