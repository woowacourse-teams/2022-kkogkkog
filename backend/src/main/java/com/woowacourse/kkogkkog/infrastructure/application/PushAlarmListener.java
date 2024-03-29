package com.woowacourse.kkogkkog.infrastructure.application;

import com.woowacourse.kkogkkog.infrastructure.event.PushAlarmEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PushAlarmListener {

    private final SlackClient slackClient;

    public PushAlarmListener(SlackClient slackClient) {
        this.slackClient = slackClient;
    }

    @EventListener
    @Async("threadPoolTaskExecutor")
    public void sendNotification(PushAlarmEvent pushAlarmEvent) {
        if (pushAlarmEvent.shouldNotSendPushAlarm()) {
            return;
        }
        String accessToken = pushAlarmEvent.getBotAccessToken();
        String hostMemberId = pushAlarmEvent.getHostMemberId();
        String message = pushAlarmEvent.getMessage();
        slackClient.requestPushAlarm(accessToken, hostMemberId, message);
    }
}
