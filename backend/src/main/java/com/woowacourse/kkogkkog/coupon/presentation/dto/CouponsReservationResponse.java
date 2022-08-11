package com.woowacourse.kkogkkog.coupon.presentation.dto;

import com.woowacourse.kkogkkog.coupon.application.dto.CouponReservationResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponsReservationResponse {

    private List<CouponReservationResponse> received;
    private List<CouponReservationResponse> sent;

    public CouponsReservationResponse(List<CouponReservationResponse> received,
                                      List<CouponReservationResponse> sent) {
        this.received = received;
        this.sent = sent;
    }
}
