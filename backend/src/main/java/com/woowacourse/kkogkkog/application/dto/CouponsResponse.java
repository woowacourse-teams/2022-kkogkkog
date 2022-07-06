package com.woowacourse.kkogkkog.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponsResponse {
    private List<CouponResponse> data;

    public CouponsResponse(List<CouponResponse> data) {
        this.data = data;
    }
}
