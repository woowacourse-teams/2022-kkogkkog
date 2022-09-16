package com.woowacourse.kkogkkog.infrastructure.event;

import com.woowacourse.kkogkkog.coupon.domain.CouponHistory;
import com.woowacourse.kkogkkog.legacy_member.domain.LegacyMemberHistory;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PushAlarmPublisher {

    private static final String WOOWACOURSE_ID = "TFELTJB7V";

    private final ApplicationEventPublisher publisher;

    public PushAlarmPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishEvent(CouponHistory couponHistory) {
        Member hostMember = couponHistory.getHostMember();
        Workspace workspace = hostMember.getWorkspace();
        if (workspace.getWorkspaceId().equals(WOOWACOURSE_ID)) {
            publisher.publishEvent(WoowacoursePushAlarmEvent.of(couponHistory));
            return;
        }
        publisher.publishEvent(PushAlarmEvent.of(couponHistory));
    }

    public void publishEvent(LegacyMemberHistory memberHistory) {
        Member hostMember = memberHistory.getHostMember();
        Workspace workspace = hostMember.getWorkspace();
        if (workspace.getWorkspaceId().equals(WOOWACOURSE_ID)) {
            publisher.publishEvent(WoowacoursePushAlarmEvent.of(memberHistory));
            return;
        }
        publisher.publishEvent(PushAlarmEvent.of(memberHistory));
    }
}
