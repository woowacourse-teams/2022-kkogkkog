package com.woowacourse.kkogkkog.coupon.exception;

public class CouponNotFoundException extends RuntimeException {

    public CouponNotFoundException() {
        super("존재하지 않는 쿠폰입니다.");
    }
}
