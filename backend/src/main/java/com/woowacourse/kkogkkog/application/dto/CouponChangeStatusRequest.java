package com.woowacourse.kkogkkog.application.dto;

import com.woowacourse.kkogkkog.domain.CouponEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponChangeStatusRequest {

    private Long authUserId;
    private Long couponId;
    private CouponEvent event;

    public CouponChangeStatusRequest(Long authUserId, Long couponId, CouponEvent event) {
        this.authUserId = authUserId;
        this.couponId = couponId;
        this.event = event;
    }
}
