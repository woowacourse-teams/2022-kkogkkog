package com.woowacourse.kkogkkog.common.fixture.dto;

import com.woowacourse.kkogkkog.reservation.application.dto.ReservationSaveRequest;
import com.woowacourse.kkogkkog.reservation.application.dto.ReservationUpdateRequest;
import com.woowacourse.kkogkkog.reservation.presentation.dto.ReservationChangeRequest;
import com.woowacourse.kkogkkog.reservation.presentation.dto.ReservationCreateRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservationDtoFixture {

    public static ReservationCreateRequest 예약_생성_요청(Long couponId, LocalDate now) {
        return new ReservationCreateRequest(couponId, now, "예약할 때 보내는 메시지");
    }

    public static ReservationSaveRequest 예약_저장_요청(Long memberId, Long couponId, LocalDate now) {
        return new ReservationSaveRequest(memberId, couponId, now, "예약할 때 보내는 메시지");
    }

    public static ReservationChangeRequest 예약_변경_요청(String event) {
        return new ReservationChangeRequest(event, "예약 상태를 변경할 때 보내는 메시지");
    }

    public static ReservationUpdateRequest 예약_수정_요청(Long memberId,
                                                    Long reservationId,
                                                    String event) {
        return new ReservationUpdateRequest(memberId, reservationId, event, "예약수정할 때 보내는 메시지");
    }
}
