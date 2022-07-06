package com.woowacourse.kkogkkog.exception.coupon;

import com.woowacourse.kkogkkog.exception.InvalidRequestException;

public class CouponNotFoundException extends InvalidRequestException {

    public CouponNotFoundException() {
        super("존재하지 않는 쿠폰입니다.");
    }
}
