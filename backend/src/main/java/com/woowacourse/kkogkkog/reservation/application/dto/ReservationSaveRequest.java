package com.woowacourse.kkogkkog.reservation.application.dto;

import static com.woowacourse.kkogkkog.reservation.domain.ReservationStatus.IN_PROGRESS;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.reservation.domain.Reservation;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReservationSaveRequest {

    private Long memberId;
    private Long couponId;
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime meetingDate;
    private String message;

    public ReservationSaveRequest(Long memberId, Long couponId, LocalDateTime meetingDate,
                                  String message) {
        this.memberId = memberId;
        this.couponId = couponId;
        this.meetingDate = meetingDate;
        this.message = message;
    }

    public Reservation toEntity(Coupon coupon) {
        return new Reservation(null, coupon, meetingDate, message, IN_PROGRESS);
    }
}
