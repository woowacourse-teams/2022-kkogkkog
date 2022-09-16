package com.woowacourse.kkogkkog.legacy_coupon.presentation.dto;

import com.woowacourse.kkogkkog.legacy_coupon.presentation.dto.CouponsReservationResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MyCouponsReservationResponse {

    private com.woowacourse.kkogkkog.legacy_coupon.presentation.dto.CouponsReservationResponse data;

    public MyCouponsReservationResponse(CouponsReservationResponse data) {
        this.data = data;
    }
}
