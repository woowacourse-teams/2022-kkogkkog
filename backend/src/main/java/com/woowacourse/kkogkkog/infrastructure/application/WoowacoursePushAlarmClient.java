package com.woowacourse.kkogkkog.infrastructure.application;

import com.woowacourse.kkogkkog.infrastructure.dto.PushAlarmRequest;
import com.woowacourse.kkogkkog.infrastructure.exception.PostMessageRequestFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class WoowacoursePushAlarmClient implements PushAlarmClient {

    private static final String POST_MESSAGE_FAILED_CAUSE_FORMAT = "상태코드: %s, 유저 ID: %s, 보낼 메세지: %s";
    private final String token;
    private final WebClient messageClient;

    public WoowacoursePushAlarmClient(
        @Value("security.slack.workspace.woowacourse.request-url") String requestUrl,
        @Value("security.slack.workspace.woowacourse.token") String token,
        WebClient webClient) {
        this.token = token;
        this.messageClient = toWebClient(webClient, requestUrl);
    }

    @Override
    public void requestPushAlarm(String token, String userId, String message) {
            ResponseSpec response = requestToPushAlarmServer(userId, message);
            checkErrorStatusCode(response, userId, message);
    }

    private ResponseSpec requestToPushAlarmServer(String userId, String message) {
        return messageClient
            .post()
            .headers(httpHeaders -> httpHeaders.setBearerAuth(this.token))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(PushAlarmRequest.of(userId, message))
            .retrieve();
    }

    private void checkErrorStatusCode(ResponseSpec response, String userId, String message) {
        try {
        response.onStatus(HttpStatus::isError,
            status -> throwPostMessageFailedException(userId, message, status));
        } catch (PostMessageRequestFailedException e) {
            log.info("Exception has been thrown : ", e);
        }
    }

    private Mono<Throwable> throwPostMessageFailedException(String userId, String message,
                                                            ClientResponse status) {
        return Mono.error(new PostMessageRequestFailedException(
            String.format(POST_MESSAGE_FAILED_CAUSE_FORMAT, status.statusCode(), userId, message)));
    }
}
