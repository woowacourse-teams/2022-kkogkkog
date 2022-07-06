package com.woowacourse.kkogkkog.exception.coupon;

import com.woowacourse.kkogkkog.exception.InvalidRequestException;

public class CouponTypeNotFoundException extends InvalidRequestException {

    public CouponTypeNotFoundException() {
        super("존재하지 않는 쿠폰 타입입니다.");
    }
}
