package com.woowacourse.kkogkkog.application.dto;

import com.woowacourse.kkogkkog.domain.CouponEvent;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponChangeStatusRequest {

    private Long loginMemberId;
    private Long couponId;
    private CouponEvent event;
    private LocalDate meetingDate;

    public CouponChangeStatusRequest(Long loginMemberId, Long couponId, CouponEvent event) {
        this.loginMemberId = loginMemberId;
        this.couponId = couponId;
        this.event = event;
    }

    public CouponChangeStatusRequest(Long loginMemberId, Long couponId, CouponEvent event,
                                     LocalDate meetingDate) {
        this.loginMemberId = loginMemberId;
        this.couponId = couponId;
        this.event = event;
        this.meetingDate = meetingDate;
    }
}
