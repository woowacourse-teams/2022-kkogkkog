package com.woowacourse.kkogkkog.coupon2.domain;

import lombok.Getter;

@Getter
public enum CouponType {

    COFFEE("커피"),
    MEAL("식사"),
    DRINK("술");

    private final String displayName;

    CouponType(String displayName) {
        this.displayName = displayName;
    }
}
