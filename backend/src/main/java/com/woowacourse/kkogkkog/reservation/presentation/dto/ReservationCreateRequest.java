package com.woowacourse.kkogkkog.reservation.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.woowacourse.kkogkkog.reservation.application.dto.ReservationSaveRequest;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReservationCreateRequest {

    private Long couponId;
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate meetingDate;
    private String message;

    public ReservationCreateRequest(Long couponId, LocalDate meetingDate, String message) {
        this.couponId = couponId;
        this.meetingDate = meetingDate;
        this.message = message;
    }

    public ReservationSaveRequest toReservationSaveRequest() {
        return new ReservationSaveRequest(couponId, meetingDate, message);
    }
}
