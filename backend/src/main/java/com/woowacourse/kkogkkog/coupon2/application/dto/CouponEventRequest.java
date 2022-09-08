package com.woowacourse.kkogkkog.coupon2.application.dto;

import com.woowacourse.kkogkkog.coupon2.domain.CouponEvent;
import com.woowacourse.kkogkkog.coupon2.domain.CouponEventType;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CouponEventRequest {

    private final Long memberId;
    private final Long couponId;
    private final CouponEvent event;
    private final String message;

    public CouponEventRequest(Long memberId,
                              Long couponId,
                              CouponEventType eventType,
                              LocalDateTime meetingDate,
                              String message) {
        this.memberId = memberId;
        this.couponId = couponId;
        this.event = new CouponEvent(eventType, meetingDate);
        this.message = message;
    }
}
