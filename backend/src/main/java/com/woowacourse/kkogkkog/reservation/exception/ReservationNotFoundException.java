package com.woowacourse.kkogkkog.reservation.exception;

import com.woowacourse.kkogkkog.exception.NotFoundException;

public class ReservationNotFoundException extends NotFoundException {

    public ReservationNotFoundException() {
        super("존재하지 않는 예약입니다.");
    }
}
