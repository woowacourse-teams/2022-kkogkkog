package com.woowacourse.kkogkkog.coupon.domain2;

import com.woowacourse.kkogkkog.common.exception.InvalidRequestException;

public enum CouponStatus {

    READY,
    REQUESTED,
    ACCEPTED,
    FINISHED,
    ;

    public CouponStatus handle(CouponEvent event) {
        CouponEventType type = event.getType();
        if (type == CouponEventType.REQUEST) {
            return handleRequest();
        }
        if (type == CouponEventType.CANCEL) {
            return handleCancel();
        }
        if (type == CouponEventType.DECLINE) {
            return handleDecline();
        }
        if (type == CouponEventType.ACCEPT) {
            return handleAccept();
        }
        if (type == CouponEventType.FINISH) {
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
