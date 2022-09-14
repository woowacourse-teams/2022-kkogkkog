package com.woowacourse.kkogkkog.coupon.application.dto;

import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.coupon.domain.CouponEventType;
import java.time.LocalDateTime;
import java.util.prefs.PreferencesFactory;
import lombok.Getter;

@Getter
public class CouponStatusRequest {

    private final Long memberId;
    private final Long couponId;
    private final CouponEvent event;
    private final LocalDateTime meetingDate;
    private final String message;

    public CouponStatusRequest(Long memberId,
                               Long couponId,
                               CouponEventType eventType,
                               LocalDateTime meetingDate,
                               String meetingMessage) {
        this.memberId = memberId;
        this.couponId = couponId;
        this.event = new CouponEvent(eventType, meetingDate);
        this.meetingDate = meetingDate;
        this.message = meetingMessage;
    }
}
