package com.woowacourse.kkogkkog.coupon.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RegisterCouponCodeRequest {

    private String couponCode;

    public RegisterCouponCodeRequest(String couponCode) {
        this.couponCode = couponCode;
    }
}
