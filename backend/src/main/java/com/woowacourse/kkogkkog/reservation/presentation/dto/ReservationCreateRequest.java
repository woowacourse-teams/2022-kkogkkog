package com.woowacourse.kkogkkog.reservation.presentation.dto;

import com.woowacourse.kkogkkog.reservation.application.dto.ReservationSaveRequest;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReservationCreateRequest {

    private Long couponId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate meetingDate;
    private String message;

    public ReservationCreateRequest(Long couponId, LocalDate meetingDate,
                                    String message) {
        this.couponId = couponId;
        this.meetingDate = meetingDate;
        this.message = message;
    }

    public ReservationSaveRequest toReservationSaveRequest(Long memberId) {
        return new ReservationSaveRequest(memberId, couponId, meetingDate, message);
    }
}
