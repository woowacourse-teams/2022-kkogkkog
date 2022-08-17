package com.woowacourse.kkogkkog.coupon.exception;

import com.woowacourse.kkogkkog.common.exception.NotFoundException;

public class CouponNotFoundException extends NotFoundException {

    public CouponNotFoundException() {
        super("존재하지 않는 쿠폰입니다.");
    }
}
