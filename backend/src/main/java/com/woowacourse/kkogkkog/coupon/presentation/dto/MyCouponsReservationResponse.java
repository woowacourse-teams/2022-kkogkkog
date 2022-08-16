package com.woowacourse.kkogkkog.coupon.presentation.dto;

import com.woowacourse.kkogkkog.coupon.application.dto.CouponReservationResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MyCouponsReservationResponse {

    private CouponsReservationResponse data;

    public MyCouponsReservationResponse(CouponsReservationResponse data) {
        this.data = data;
    }
}
