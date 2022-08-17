package com.woowacourse.kkogkkog.support.fixture.domain;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.reservation.domain.Reservation;
import java.time.LocalDateTime;

public enum ReservationFixture {

    RESERVE_SAVE("예약을 생성할 때 사용하는 메시지입니다.")
    ;

    private String message;

    ReservationFixture(String message) {
        this.message = message;
    }

    public Reservation getReservation(Coupon coupon, LocalDateTime meetingDate) {
        return new Reservation(null, coupon, meetingDate, message);
    }
}
