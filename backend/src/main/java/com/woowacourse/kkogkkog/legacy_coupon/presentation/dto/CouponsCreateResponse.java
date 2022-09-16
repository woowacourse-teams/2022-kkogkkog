package com.woowacourse.kkogkkog.legacy_coupon.presentation.dto;

import com.woowacourse.kkogkkog.legacy_coupon.application.dto.CouponResponse;
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
