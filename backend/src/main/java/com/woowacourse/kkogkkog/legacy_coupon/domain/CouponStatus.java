package com.woowacourse.kkogkkog.legacy_coupon.domain;

import com.woowacourse.kkogkkog.common.exception.InvalidRequestException;
import com.woowacourse.kkogkkog.coupon.domain.CouponEventType;

public enum CouponStatus {

    READY,
    REQUESTED,
    ACCEPTED,
    FINISHED,
    ;

    public CouponStatus handle(CouponEventType event) {
        if (event == CouponEventType.REQUEST) {
            return handleRequest();
        }
        if (event == CouponEventType.CANCEL) {
            return handleCancel();
        }
        if (event == CouponEventType.DECLINE) {
            return handleDecline();
        }
        if (event == CouponEventType.ACCEPT) {
            return handleAccept();
        }
        if (event == CouponEventType.FINISH) {
            return handleFinish();
        }
        throw new InvalidRequestException("처리할 수 없는 요청입니다.");
    }

    private CouponStatus handleRequest() {
        if (this != READY) {
            throw new InvalidRequestException("사용 요청을 보낼 수 없는 상태의 쿠폰입니다.");
        }
        return REQUESTED;
    }

    private CouponStatus handleCancel() {
        if (this != REQUESTED && this != ACCEPTED) {
            throw new InvalidRequestException("사용 요청을 취소할 수 없는 상태의 쿠폰입니다.");
        }
        return READY;
    }

    private CouponStatus handleDecline() {
        if (this != REQUESTED) {
            throw new InvalidRequestException("사용 요청을 거절할 수 없는 상태의 쿠폰입니다.");
        }
        return READY;
    }

    private CouponStatus handleAccept() {
        if (this != REQUESTED) {
            throw new InvalidRequestException("사용 요청 상태의 쿠폰이 아닙니다.");
        }
        return ACCEPTED;
    }

    private CouponStatus handleFinish() {
        if (this == FINISHED) {
            throw new InvalidRequestException("이미 사용 완료된 쿠폰입니다.");
        }
        return FINISHED;
    }
}
