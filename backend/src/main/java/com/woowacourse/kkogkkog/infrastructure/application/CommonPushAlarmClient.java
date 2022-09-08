package com.woowacourse.kkogkkog.infrastructure.application;

import com.woowacourse.kkogkkog.infrastructure.dto.PushAlarmRequest;
import com.woowacourse.kkogkkog.infrastructure.exception.PostMessageRequestFailedException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class CommonPushAlarmClient implements PushAlarmClient {

    private static final String PUSH_ALARM_REQUEST_URL = "https://slack.com/api/chat.postMessage";


    private final WebClient messageClient;

    private static final ParameterizedTypeReference<Map<String, Object>> PARAMETERIZED_TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };

    public CommonPushAlarmClient(@Value(PUSH_ALARM_REQUEST_URL) String requestUrl, WebClient webClient) {
        this.messageClient = toWebClient(webClient, requestUrl);
    }

    @Override
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
}
