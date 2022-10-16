package com.woowacourse.kkogkkog.unregisteredcoupon;

import com.woowacourse.kkogkkog.common.exception.ForbiddenException;

public class UnregisteredCouponNotAccessibleException extends ForbiddenException {

    public UnregisteredCouponNotAccessibleException() {
        super("접근할 수 없는 미등록 쿠폰입니다.");
    }
}
