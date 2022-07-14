package com.woowacourse.kkogkkog.exception.coupon;

import com.woowacourse.kkogkkog.exception.NotFoundException;

public class CouponNotFoundException extends NotFoundException {

    public CouponNotFoundException() {
        super("존재하지 않는 쿠폰입니다.");
    }
}
