package com.woowacourse.kkogkkog.reservation.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReservationUpdateRequest {

    private Long memberId;
    private Long reservationId;
    private String event;
    private String message;

    public ReservationUpdateRequest(Long memberId, Long reservationId, String event,
                                    String message) {
        this.memberId = memberId;
        this.reservationId = reservationId;
        this.event = event;
        this.message = message;
    }
}
