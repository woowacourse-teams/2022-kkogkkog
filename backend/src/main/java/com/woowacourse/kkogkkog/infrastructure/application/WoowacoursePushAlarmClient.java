package com.woowacourse.kkogkkog.infrastructure.application;

import com.woowacourse.kkogkkog.infrastructure.dto.WoowacourseUsersResponse;
import com.woowacourse.kkogkkog.infrastructure.dto.PushAlarmRequest;
import com.woowacourse.kkogkkog.infrastructure.exception.WoowacoursePostMessageRequestFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class WoowacoursePushAlarmClient {

    private static final String POST_MESSAGE_FAILED_CAUSE_FORMAT = "상태코드: %s, 유저 ID: %s, 보낼 메세지: %s";

    private final String token;
    private final WebClient messageClient;

    public WoowacoursePushAlarmClient(
        @Value("${security.slack.workspace.woowacourse.token}") String token,
        @Value("${security.slack.workspace.woowacourse.request-url}") String requestUrl,
        WebClient webClient) {
        this.token = token;
        this.messageClient = toWebClient(webClient, requestUrl);
    }

    private WebClient toWebClient(WebClient webClient, String baseUrl) {
        return webClient.mutate()
            .baseUrl(baseUrl)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    public void requestPushAlarm(String userId, String message) {
        ResponseSpec response = requestToPushAlarmServer(userId, message);
        checkErrorStatusCode(response, userId, message);
    }

    public WoowacourseUsersResponse requestUsers() {
        return messageClient
            .get()
            .uri(uriBuilder -> uriBuilder.path("/api/users").build())
            .headers(httpHeaders -> httpHeaders.set(HttpHeaders.AUTHORIZATION, this.token))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(WoowacourseUsersResponse.class)
            .blockOptional()
            .orElseThrow(() -> new RuntimeException("우아한테크코스 통합 알림봇에 문제가 있습니다."));
    }

    private ResponseSpec requestToPushAlarmServer(String userId, String message) {
        return messageClient
            .post()
            .uri(uriBuilder -> uriBuilder.path("/api/send").build())
            .headers(httpHeaders -> httpHeaders.set(HttpHeaders.AUTHORIZATION, this.token))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(PushAlarmRequest.of(userId, message))
            .retrieve();
    }

    private void checkErrorStatusCode(ResponseSpec response, String userId, String message) {
        try {
            response.onStatus(HttpStatus::isError,
                    status -> throwPostMessageFailedException(userId, message, status))
                .toBodilessEntity()
                .blockOptional();
        } catch (WoowacoursePostMessageRequestFailedException e) {
            log.info("Exception has been thrown : ", e);
        }
    }

    private Mono<Throwable> throwPostMessageFailedException(String userId, String message,
                                                            ClientResponse status) {
        return Mono.error(new WoowacoursePostMessageRequestFailedException(
            String.format(POST_MESSAGE_FAILED_CAUSE_FORMAT, status.statusCode(), userId, message)));
    }
}
