package com.woowacourse.kkogkkog.coupon2.presentation.dto;

import com.woowacourse.kkogkkog.coupon2.application.dto.CouponEventRequest;
import com.woowacourse.kkogkkog.coupon2.domain.CouponEventType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponUpdateRequest {

    private String event;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate meetingDate;
    private String message;

    public CouponUpdateRequest(String event, LocalDate meetingDate,
                               String message) {
        this.event = event;
        this.meetingDate = meetingDate;
        this.message = message;
    }

    public CouponEventRequest toCouponEventRequest(Long memberId, Long couponId) {
        return new CouponEventRequest(
            memberId,
            couponId,
            CouponEventType.of(event),
            convertMeetingDate(), message);
    }

    private LocalDateTime convertMeetingDate() {
        if (meetingDate == null) {
            return null;
        }
        return meetingDate.atStartOfDay();
    }
}
