package com.woowacourse.kkogkkog.coupon.presentation.dto;

import com.woowacourse.kkogkkog.unregisteredcoupon.UnregisteredCouponResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UnregisteredCouponsResponse {

    private List<UnregisteredCouponResponse> data;

    public UnregisteredCouponsResponse(final List<UnregisteredCouponResponse> data) {
        this.data = data;
    }
}
