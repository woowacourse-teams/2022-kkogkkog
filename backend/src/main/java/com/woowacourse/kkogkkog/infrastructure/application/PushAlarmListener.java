package com.woowacourse.kkogkkog.infrastructure.application;

import com.woowacourse.kkogkkog.infrastructure.event.PushAlarmEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PushAlarmListener {

    private final CommonPushAlarmClient commonPushAlarmClient;
    private final WoowacoursePushAlarmClient woowacoursePushAlarmClient;

    public PushAlarmListener(CommonPushAlarmClient commonPushAlarmClient,
                             WoowacoursePushAlarmClient woowacoursePushAlarmClient) {
        this.commonPushAlarmClient = commonPushAlarmClient;
        this.woowacoursePushAlarmClient = woowacoursePushAlarmClient;
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
        if (pushAlarmEvent.isWoowacourseWorkspace()) {
            woowacoursePushAlarmClient.requestPushAlarm(accessToken, hostMemberId, message);
        }
        if (pushAlarmEvent.hasBotAccessToken()) {
            commonPushAlarmClient.requestPushAlarm(accessToken, hostMemberId, message);
        }

    }
}
