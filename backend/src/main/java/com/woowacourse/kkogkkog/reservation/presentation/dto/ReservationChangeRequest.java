package com.woowacourse.kkogkkog.reservation.presentation.dto;

import com.woowacourse.kkogkkog.reservation.application.dto.ReservationUpdateRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReservationChangeRequest {

    private String event;
    private String message;

    public ReservationChangeRequest(String event, String message) {
        this.event = event;
        this.message = message;
    }

    public ReservationUpdateRequest toReservationUpdateRequest(Long reservationId,
                                                               Long loginMemberId) {
        return new ReservationUpdateRequest(loginMemberId, reservationId, event, message);
    }
}
