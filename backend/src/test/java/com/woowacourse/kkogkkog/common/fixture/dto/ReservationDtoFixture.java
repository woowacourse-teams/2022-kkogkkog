package com.woowacourse.kkogkkog.common.fixture.dto;

import com.woowacourse.kkogkkog.reservation.application.dto.ReservationSaveRequest;
import java.time.LocalDate;

public class ReservationDtoFixture {

    public static ReservationSaveRequest 예약_저장_요청(Long id, LocalDate now) {
        return new ReservationSaveRequest(id, now, "예약할 때 보내는 메시지");
    }
}
