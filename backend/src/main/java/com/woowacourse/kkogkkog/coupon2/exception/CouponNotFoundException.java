package com.woowacourse.kkogkkog.coupon2.exception;

public class CouponNotFoundException extends RuntimeException {

    public CouponNotFoundException() {
        super("존재하지 않는 쿠폰입니다.");
    }
}
