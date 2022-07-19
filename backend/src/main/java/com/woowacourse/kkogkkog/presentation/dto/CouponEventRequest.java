package com.woowacourse.kkogkkog.presentation.dto;

import com.woowacourse.kkogkkog.application.dto.CouponChangeStatusRequest;
import com.woowacourse.kkogkkog.domain.CouponEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponEventRequest {

    private String couponEvent;

    public CouponEventRequest(String couponEvent) {
        this.couponEvent = couponEvent;
    }

    public CouponChangeStatusRequest toCouponChangeStatusRequest(Long authUserId, Long couponId) {
        return new CouponChangeStatusRequest(authUserId, couponId, CouponEvent.of(couponEvent));
    }
}
