package com.woowacourse.kkogkkog.coupon2.presentation.dto;

import com.woowacourse.kkogkkog.coupon2.application.dto.CouponResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MyCouponsResponse {

    private CouponsResponse data;

    public MyCouponsResponse(CouponsResponse data) {
        this.data = data;
    }

    public MyCouponsResponse(List<CouponResponse> received,
                             List<CouponResponse> sent) {
        this(new CouponsResponse(received, sent));
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    private static class CouponsResponse {

        private List<CouponResponse> received;
        private List<CouponResponse> sent;

        public CouponsResponse(List<CouponResponse> received,
                               List<CouponResponse> sent) {
            this.received = received;
            this.sent = sent;
        }
    }
}
