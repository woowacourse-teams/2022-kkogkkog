package com.woowacourse.kkogkkog.coupon.exception;

public class UnregisteredCouponNotFoundException extends RuntimeException {

    public UnregisteredCouponNotFoundException() {
        super("존재하지 않는 무기명 쿠폰입니다.");
    }
}
