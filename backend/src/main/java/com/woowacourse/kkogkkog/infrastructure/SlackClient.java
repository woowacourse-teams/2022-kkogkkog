package com.woowacourse.kkogkkog.infrastructure;

import com.woowacourse.kkogkkog.exception.auth.AccessTokenRequestFailedException;
import com.woowacourse.kkogkkog.exception.auth.AccessTokenRetrievalFailedException;
import com.woowacourse.kkogkkog.exception.auth.BotInstallationFailedException;
import com.woowacourse.kkogkkog.exception.auth.OAuthUserInfoRequestFailedException;
import com.woowacourse.kkogkkog.exception.infrastructure.PostMessageRequestFailedException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

@Component
public class SlackClient {

    private static final String LOGIN_URI = "https://slack.com/api/openid.connect.token";
    private static final String LOGIN_USER_INFO_URI = "https://slack.com/api/openid.connect.userInfo";
    private static final String BOT_TOKEN_URI = "https://slack.com/api/oauth.v2.access";
    private static final String MESSAGE_URI = "https://slack.com/api/chat.postMessage";

    private static final String LOGIN_REDIRECT_URL = "/login/redirect";
    private static final String BOT_TOKEN_REDIRECT_URL = "/download/redirect";

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
                       Environment env,
                       WebClient webClient) {
        String activeProfile = env.getActiveProfiles()[0];
        this.clientId = clientId;
        this.secretId = secretId;
        this.oAuthLoginClient = toWebClient(webClient, oAuthLoginUri);
        this.userClient = toWebClient(webClient, userInfoUri);
        this.botTokenClient = toWebClient(webClient, botTokenUri);
        this.messageClient = toWebClient(webClient, messageUri);
        this.loginRedirectUrl = toPublicDomain(activeProfile) + LOGIN_REDIRECT_URL;
        this.botTokenRedirectUrl = toPublicDomain(activeProfile) + BOT_TOKEN_REDIRECT_URL;
    }

    private WebClient toWebClient(WebClient webClient, String baseUrl) {
        return webClient.mutate()
            .baseUrl(baseUrl)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    private String toPublicDomain(String activeProfile) {
        if (activeProfile.equals("prod")) {
            return "https://kkogkkog.com";
        }
        return "https://dev.kkogkkog.com";
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
        validateResponseBody(responseBody);

        return responseBody.get("access_token").toString();
    }

    private void validateResponseBody(Map<String, Object> responseBody) {
        if (!responseBody.containsKey("access_token")) {
            throw new AccessTokenRetrievalFailedException("슬랙 서버로부터 토큰 조회에 실패하였습니다.");
            // TODO: responseBody.get("error") 값 활용하여 로그 남기기
        }
    }

    private SlackUserInfo requestUserInfo(String token) {
        return userClient
            .get()
            .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
            .retrieve()
            .bodyToMono(SlackUserInfo.class)
            .blockOptional()
            .orElseThrow(OAuthUserInfoRequestFailedException::new);
    }

    public WorkspaceResponse requestBotAccessToken(String code) {
        BotTokenResponse botTokenResponse = botTokenClient
            .post()
            .uri(uriBuilder -> toRequestTokenUri(uriBuilder, code, botTokenRedirectUrl))
            .headers(this::setHeaders)
            .retrieve()
            .bodyToMono(BotTokenResponse.class)
            .blockOptional()
            .orElseThrow(AccessTokenRequestFailedException::new);

        if (!botTokenResponse.getOk()) {
            throw new BotInstallationFailedException("슬랙 봇 등록에 실패하였습니다.");
        }
        return new WorkspaceResponse(botTokenResponse.getTeam().getId(),
            botTokenResponse.getTeam().getName(),
            botTokenResponse.getAccessToken());
    }

    public void requestPushAlarm(String token, String userId, String message) {
        try {
            messageClient
                .post()
                .uri(uriBuilder -> toRequestPostMessageUri(uriBuilder, userId, message))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .retrieve()
                .bodyToMono(PARAMETERIZED_TYPE_REFERENCE)
                .blockOptional()
                .orElseThrow(PostMessageRequestFailedException::new);
        } catch (PostMessageRequestFailedException e) {
            e.printStackTrace(); // TODO: 사용자에게 예외 던지지 말고 로그만 찍기. 향후 로그백으로 대체.
        }
    }

    private URI toRequestPostMessageUri(UriBuilder uriBuilder, String userId, String message) {
        return uriBuilder
            .queryParam(USER_ID_PARAMETER, userId)
            .queryParam(MESSAGE_PARAMETER, message)
            .build();
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
