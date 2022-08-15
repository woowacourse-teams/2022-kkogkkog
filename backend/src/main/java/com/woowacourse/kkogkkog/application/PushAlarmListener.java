package com.woowacourse.kkogkkog.application;

import com.woowacourse.kkogkkog.application.PushAlarmEvent;
import com.woowacourse.kkogkkog.infrastructure.SlackClient;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PushAlarmListener {

    private final SlackClient slackClient;

    public PushAlarmListener(SlackClient slackClient) {
        this.slackClient = slackClient;
    }

    @EventListener
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
