package com.woowacourse.kkogkkog.lazycoupon.domain;

import com.woowacourse.kkogkkog.common.exception.InvalidRequestException;

public enum LazyCouponStatus {

    ISSUED,
    REGISTERED,
    EXPIRED,
    ;

    public LazyCouponStatus handle(LazyCouponEventType eventType) {
        if (eventType == LazyCouponEventType.REGISTER) {
            return handleRegister();
        }
        if (eventType == LazyCouponEventType.EXPIRE) {
            return handleExpire();
        }
        throw new InvalidRequestException("처리할 수 없는 요청입니다.");
    }

    private LazyCouponStatus handleRegister() {
        if (this != ISSUED) {
            throw new InvalidRequestException("등록 할 수 없는 상태의 미등록 쿠폰입니다.");
        }
        return REGISTERED;
    }

    private LazyCouponStatus handleExpire() {
        if (this != ISSUED) {
            throw new InvalidRequestException("만료 될 수 없는 상태의 미등록 쿠폰입니다.");
        }
        return EXPIRED;
    }
}
