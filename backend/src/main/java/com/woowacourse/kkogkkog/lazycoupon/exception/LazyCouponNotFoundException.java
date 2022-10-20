package com.woowacourse.kkogkkog.lazycoupon.exception;

import com.woowacourse.kkogkkog.common.exception.NotFoundException;

public class LazyCouponNotFoundException extends NotFoundException {

    public LazyCouponNotFoundException() {
        super("존재하지 않는 미등록 쿠폰입니다.");
    }
}
