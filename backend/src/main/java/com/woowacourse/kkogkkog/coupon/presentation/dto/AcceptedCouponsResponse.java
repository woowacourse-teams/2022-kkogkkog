package com.woowacourse.kkogkkog.coupon.presentation.dto;

import com.woowacourse.kkogkkog.coupon.application.dto.AcceptedCouponResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AcceptedCouponsResponse {

    private List<AcceptedCouponResponse> data;

    public AcceptedCouponsResponse(final List<AcceptedCouponResponse> data) {
        this.data = data;
    }
}
