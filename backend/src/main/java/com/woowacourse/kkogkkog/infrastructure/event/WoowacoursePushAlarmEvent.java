package com.woowacourse.kkogkkog.infrastructure.event;

import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.MemberHistory;
import lombok.Getter;

@Getter
public class WoowacoursePushAlarmEvent {

    private final String hostMemberId;
    private final String message;
    private final CouponEvent couponEvent;

    private WoowacoursePushAlarmEvent(String hostMemberId, String message, CouponEvent couponEvent) {
        this.hostMemberId = hostMemberId;
        this.message = message;
        this.couponEvent = couponEvent;
    }

    protected static WoowacoursePushAlarmEvent of(MemberHistory memberHistory) {
        Member hostMember = memberHistory.getHostMember();
        return new WoowacoursePushAlarmEvent(hostMember.getUserId(),
            memberHistory.toNoticeMessage(), memberHistory.getCouponEvent());
    }

    public boolean shouldNotSendPushAlarm() {
        return couponEvent == CouponEvent.FINISH;
    }
}
