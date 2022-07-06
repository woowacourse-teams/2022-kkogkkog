package com.woowacourse.kkogkkog.exception;

public class CouponTypeNotFoundException extends RuntimeException {

    public CouponTypeNotFoundException() {
        super("존재하지 않는 쿠폰 타입입니다.");
    }
}
