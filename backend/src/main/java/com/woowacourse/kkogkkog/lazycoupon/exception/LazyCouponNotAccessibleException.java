package com.woowacourse.kkogkkog.lazycoupon.exception;

import com.woowacourse.kkogkkog.common.exception.ForbiddenException;

public class LazyCouponNotAccessibleException extends ForbiddenException {

    public LazyCouponNotAccessibleException() {
        super("접근할 수 없는 미등록 쿠폰입니다.");
    }
}
