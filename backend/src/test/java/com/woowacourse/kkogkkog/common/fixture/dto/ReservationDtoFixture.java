package com.woowacourse.kkogkkog.common.fixture.dto;

import com.woowacourse.kkogkkog.reservation.application.dto.ReservationSaveRequest;
import com.woowacourse.kkogkkog.reservation.presentation.dto.ReservationCreateRequest;
import java.time.LocalDate;

public class ReservationDtoFixture {

    public static ReservationCreateRequest 예약_생성_요청(Long couponId, LocalDate now) {
        return new ReservationCreateRequest(couponId, now, "예약할 때 보내는 메시지");
    }

    public static ReservationSaveRequest 예약_저장_요청(Long memberId, Long couponId, LocalDate now) {
        return new ReservationSaveRequest(memberId, couponId, now, "예약할 때 보내는 메시지");
    }
}
