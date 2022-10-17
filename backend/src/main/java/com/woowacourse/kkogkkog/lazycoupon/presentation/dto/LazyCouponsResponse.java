package com.woowacourse.kkogkkog.lazycoupon.presentation.dto;

import com.woowacourse.kkogkkog.lazycoupon.application.dto.LazyCouponResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LazyCouponsResponse {

    private List<LazyCouponResponse> data;

    public LazyCouponsResponse(final List<LazyCouponResponse> data) {
        this.data = data;
    }
}
