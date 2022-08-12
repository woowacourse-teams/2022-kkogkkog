package com.woowacourse.kkogkkog.reservation.domain;

import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.exception.InvalidRequestException;

public enum ReservationStatus {

    IN_PROGRESS,
    CANCEL,
    DONE
    ;

    public ReservationStatus handle(CouponEvent event) {
        if (event == CouponEvent.CANCEL) {
            return handleCancel();
        }
        if (event == CouponEvent.DECLINE) {
            return handleDecline();
        }
        if (event == CouponEvent.ACCEPT) {
            return handleAccept();
        }
        if (event == CouponEvent.FINISH) {
            return handleFinish();
        }
        throw new InvalidRequestException("처리할 수 없는 요청입니다.");
    }

    private ReservationStatus handleCancel() {
        if (this != IN_PROGRESS) {
            throw new InvalidRequestException("취소를 할 수 없는 상태의 예약입니다.");
        }
        return CANCEL;
    }

    private ReservationStatus handleDecline() {
        if (this != IN_PROGRESS) {
            throw new InvalidRequestException("거절을 할 수 없는 상태의 예약입니다.");
        }
        return CANCEL;
    }

    private ReservationStatus handleAccept() {
        if (this != IN_PROGRESS) {
            throw new InvalidRequestException("승인을 할 수 없는 상태의 예약입니다.");
        }
        return IN_PROGRESS;
    }

    private ReservationStatus handleFinish() {
        if (this == DONE || this == CANCEL) {
            throw new InvalidRequestException("이미 만료된 예약입니다.");
        }
        return DONE;
    }
}
