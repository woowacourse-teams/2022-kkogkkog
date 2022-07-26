package com.woowacourse.kkogkkog.presentation.dto;

import com.woowacourse.kkogkkog.application.dto.CouponResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MyCouponsResponse {

    private CouponsResponse data;

    public MyCouponsResponse(List<CouponResponse> received,
                             List<CouponResponse> sent) {
        this.data = new CouponsResponse(received, sent);
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    private static class CouponsResponse {

        List<CouponResponse> received;
        List<CouponResponse> sent;

        CouponsResponse(List<CouponResponse> received,
                        List<CouponResponse> sent) {
            this.received = received;
            this.sent = sent;
        }
    }
}
