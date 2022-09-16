package com.woowacourse.kkogkkog.infrastructure.application;

import com.woowacourse.kkogkkog.infrastructure.event.WoowacoursePushAlarmEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class WoowacoursePushAlarmListener {

    private final WoowacoursePushAlarmClient woowacoursePushAlarmClient;

    public WoowacoursePushAlarmListener(WoowacoursePushAlarmClient woowacoursePushAlarmClient) {
        this.woowacoursePushAlarmClient = woowacoursePushAlarmClient;
    }

    @EventListener
    @Async("threadPoolTaskExecutor")
    public void sendNotification(WoowacoursePushAlarmEvent pushAlarmEvent) {
        if (pushAlarmEvent.shouldNotSendPushAlarm()) {
            return;
        }
        String hostMemberId = pushAlarmEvent.getHostMemberId();
        String message = pushAlarmEvent.getMessage();
        woowacoursePushAlarmClient.requestPushAlarm(hostMemberId, message);
    }
}
