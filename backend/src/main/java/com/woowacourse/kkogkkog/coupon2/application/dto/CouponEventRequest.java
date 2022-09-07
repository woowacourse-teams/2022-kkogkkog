package com.woowacourse.kkogkkog.coupon2.application.dto;

import com.woowacourse.kkogkkog.coupon2.domain.CouponEvent;
import com.woowacourse.kkogkkog.coupon2.domain.CouponEventType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponEventRequest {

    private Long memberId;
    private Long couponId;
    private CouponEventType eventType;
    private LocalDateTime meetingDate;
    private String message;

    public CouponEventRequest(Long memberId,
                              Long couponId,
                              CouponEventType eventType,
                              LocalDateTime meetingDate,
                              String message) {
        this.memberId = memberId;
        this.couponId = couponId;
        this.eventType = eventType;
        this.meetingDate = meetingDate;
        this.message = message;
    }

    public CouponEvent toEvent() {
        return new CouponEvent(eventType, meetingDate);
    }
}
