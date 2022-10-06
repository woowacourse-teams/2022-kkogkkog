package com.woowacourse.kkogkkog.coupon.exception;

import com.woowacourse.kkogkkog.common.exception.NotFoundException;

public class UnregisteredCouponNotFoundException extends NotFoundException {

    public UnregisteredCouponNotFoundException() {
        super("존재하지 않는 미등록 쿠폰입니다.");
    }
}
