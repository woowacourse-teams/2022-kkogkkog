package com.woowacourse.kkogkkog.unregisteredcoupon.presentation.dto;

import com.woowacourse.kkogkkog.unregisteredcoupon.application.dto.UnregisteredCouponResponse;
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
