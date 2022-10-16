package com.woowacourse.kkogkkog.unregisteredcoupon.exception;

import com.woowacourse.kkogkkog.common.exception.InvalidRequestException;

public class UnregisteredCouponQuantityExcessException extends InvalidRequestException {

    public UnregisteredCouponQuantityExcessException() {
        super("미등록 쿠폰을 한번에 발급 가능한 수량을 초과하였습니다.");
    }
}
