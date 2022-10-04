package com.woowacourse.kkogkkog.coupon.exception;

import com.woowacourse.kkogkkog.common.exception.InvalidRequestException;

public class UnregisteredCouponQuantityExcessException extends InvalidRequestException {

    public UnregisteredCouponQuantityExcessException() {
        super("무기명 쿠폰을 한번에 발급 가능한 수량을 초과하였습니다.");
    }
}
