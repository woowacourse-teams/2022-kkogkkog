package com.woowacourse.kkogkkog.infrastructure.event;

import com.woowacourse.kkogkkog.coupon.domain.CouponEventType;
import com.woowacourse.kkogkkog.coupon.domain.CouponHistory;
import com.woowacourse.kkogkkog.member.domain.Member;
import lombok.Getter;

@Getter
public class WoowacoursePushAlarmEvent {

    private final String hostMemberId;
    private final String message;
    private final CouponEventType couponEvent;

    private WoowacoursePushAlarmEvent(String hostMemberId, String message, CouponEventType couponEvent) {
        this.hostMemberId = hostMemberId;
        this.message = message;
        this.couponEvent = couponEvent;
    }

    public static WoowacoursePushAlarmEvent of(CouponHistory couponHistory) {
        Member hostMember = couponHistory.getHostMember();
        return new WoowacoursePushAlarmEvent(hostMember.getUserId(),
            couponHistory.toNoticeMessage(), couponHistory.getCouponEventType());
    }

    public boolean shouldNotSendPushAlarm() {
        return couponEvent == CouponEventType.FINISH;
    }
}
