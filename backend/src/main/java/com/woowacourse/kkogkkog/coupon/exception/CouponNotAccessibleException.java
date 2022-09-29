package com.woowacourse.kkogkkog.coupon.exception;

import com.woowacourse.kkogkkog.common.exception.ForbiddenException;

public class CouponNotAccessibleException extends ForbiddenException {

    public CouponNotAccessibleException() {
        super("접근할 수 없는 쿠폰입니다.");
    }
}
