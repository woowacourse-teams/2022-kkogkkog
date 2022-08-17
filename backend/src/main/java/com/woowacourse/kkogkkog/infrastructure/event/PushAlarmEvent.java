package com.woowacourse.kkogkkog.infrastructure.event;

import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.MemberHistory;
import com.woowacourse.kkogkkog.domain.Workspace;
import lombok.Getter;

@Getter
public class PushAlarmEvent {

    private final String botAccessToken;
    private final String hostMemberId;
    private final String message;
    private final CouponEvent couponEvent;

    public PushAlarmEvent(String botAccessToken, String hostMemberId, String message,
                          CouponEvent couponEvent) {
        this.botAccessToken = botAccessToken;
        this.hostMemberId = hostMemberId;
        this.message = message;
        this.couponEvent = couponEvent;
    }


    public static PushAlarmEvent of(MemberHistory memberHistory) {
        Member hostMember = memberHistory.getHostMember();
        Workspace workspace = hostMember.getWorkspace();
        return new PushAlarmEvent(workspace.getAccessToken(), hostMember.getUserId(),
            memberHistory.toNoticeMessage(), memberHistory.getCouponEvent());
    }

    public boolean shouldNotSendPushAlarm() {
        return botAccessToken == null || couponEvent == CouponEvent.FINISH;
    }
}
