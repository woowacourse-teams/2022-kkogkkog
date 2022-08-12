package com.woowacourse.kkogkkog.reservation.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.woowacourse.kkogkkog.reservation.application.dto.ReservationSaveRequest;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReservationCreateRequest {

    private Long couponId;
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime meetingDate;
    private String message;

    public ReservationCreateRequest(Long couponId, LocalDateTime meetingDate,
                                    String message) {
        this.couponId = couponId;
        this.meetingDate = meetingDate;
        this.message = message;
    }

    public ReservationSaveRequest toReservationSaveRequest(Long memberId) {
        return new ReservationSaveRequest(memberId, couponId, meetingDate, message);
    }
}
