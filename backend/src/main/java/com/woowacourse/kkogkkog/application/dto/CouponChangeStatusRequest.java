package com.woowacourse.kkogkkog.application.dto;

import com.woowacourse.kkogkkog.domain.CouponEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponChangeStatusRequest {

    private Long loginMemberId;
    private Long couponId;
    private CouponEvent event;

    public CouponChangeStatusRequest(Long loginMemberId, Long couponId, CouponEvent event) {
        this.loginMemberId = loginMemberId;
        this.couponId = couponId;
        this.event = event;
    }
}
