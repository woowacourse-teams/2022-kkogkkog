package com.woowacourse.kkogkkog.lazycoupon.exception;

import com.woowacourse.kkogkkog.common.exception.InvalidRequestException;

public class LazyCouponQuantityExcessException extends InvalidRequestException {

    public LazyCouponQuantityExcessException() {
        super("미등록 쿠폰을 한번에 발급 가능한 수량을 초과하였습니다.");
    }
}
