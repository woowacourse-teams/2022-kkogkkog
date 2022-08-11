package com.woowacourse.kkogkkog.coupon.presentation.dto;

import com.woowacourse.kkogkkog.coupon.application.dto.CouponResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponsCreateResponse {

    private List<CouponResponse> data;

    public CouponsCreateResponse(List<CouponResponse> data) {
        this.data = data;
    }
}
