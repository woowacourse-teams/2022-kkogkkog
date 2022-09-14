package com.woowacourse.kkogkkog.coupon.presentation.dto;

import com.woowacourse.kkogkkog.coupon.application.dto.CouponStatusRequest;
import com.woowacourse.kkogkkog.coupon.domain.CouponEventType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponEventRequest {

    private String event;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate meetingDate;
    private String meetingMessage;

    public CouponEventRequest(String event,
                              LocalDate meetingDate,
                              String meetingMessage) {
        this.event = event;
        this.meetingDate = meetingDate;
        this.meetingMessage = meetingMessage;
    }

    public CouponStatusRequest toCouponStatusRequest(Long memberId, Long couponId) {
        return new CouponStatusRequest(
            memberId,
            couponId,
            CouponEventType.of(event),
            convertMeetingDate(),
            meetingMessage);
    }

    private LocalDateTime convertMeetingDate() {
        if (meetingDate == null) {
            return null;
        }
        return meetingDate.atStartOfDay();
    }
}
