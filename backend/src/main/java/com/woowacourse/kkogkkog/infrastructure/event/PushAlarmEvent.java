package com.woowacourse.kkogkkog.infrastructure.event;

import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.MemberHistory;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;

@Getter
@Transactional
public class PushAlarmEvent {

    private final String botAccessToken;
    private final String hostMemberId;
    private final String message;
    private final CouponEvent couponEvent;

    private PushAlarmEvent(String botAccessToken, String hostMemberId, String message,
                          CouponEvent couponEvent) {
        this.botAccessToken = botAccessToken;
        this.hostMemberId = hostMemberId;
        this.message = message;
        this.couponEvent = couponEvent;
    }

    protected static PushAlarmEvent of(MemberHistory memberHistory) {
        Member hostMember = memberHistory.getHostMember();
        Workspace workspace = hostMember.getWorkspace();
        return new PushAlarmEvent(workspace.getAccessToken(), hostMember.getUserId(),
            memberHistory.toNoticeMessage(), memberHistory.getCouponEvent());
    }

    public boolean shouldNotSendPushAlarm() {
        return botAccessToken == null || couponEvent == CouponEvent.FINISH;
    }
}
