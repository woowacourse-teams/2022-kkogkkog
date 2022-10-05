package com.woowacourse.kkogkkog.coupon.domain;

import com.woowacourse.kkogkkog.common.exception.InvalidRequestException;

public enum UnregisteredCouponStatus {

    ISSUED,
    CONSUMED,
    EXPIRED,
    ;

    public UnregisteredCouponStatus handle(UnregisteredCouponEventType eventType) {
        if (eventType == UnregisteredCouponEventType.CONSUME) {
            return handleConsume();
        }
        if (eventType == UnregisteredCouponEventType.EXPIRE) {
            return handleExpire();
        }
        throw new InvalidRequestException("처리할 수 없는 요청입니다.");
    }

    private UnregisteredCouponStatus handleConsume() {
        if (this != ISSUED) {
            throw new InvalidRequestException("사용 할 수 없는 상태의 미등록 쿠폰입니다.");
        }
        return CONSUMED;
    }

    private UnregisteredCouponStatus handleExpire() {
        if (this != ISSUED) {
            throw new InvalidRequestException("만료 될 수 없는 상태의 미등록 쿠폰입니다.");
        }
        return EXPIRED;
    }
}
