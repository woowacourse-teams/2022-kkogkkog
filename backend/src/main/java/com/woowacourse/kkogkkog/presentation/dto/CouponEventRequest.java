package com.woowacourse.kkogkkog.presentation.dto;

import com.woowacourse.kkogkkog.application.dto.CouponChangeStatusRequest;
import com.woowacourse.kkogkkog.domain.CouponEvent;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponEventRequest {

    private String couponEvent;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate meetingDate;

    public CouponEventRequest(String couponEvent) {
        this.couponEvent = couponEvent;
    }

    public CouponEventRequest(String couponEvent, LocalDate meetingDate) {
        this.couponEvent = couponEvent;
        this.meetingDate = meetingDate;
    }

    public CouponChangeStatusRequest toCouponChangeStatusRequest(Long loginMemberId,
                                                                 Long couponId) {
        return new CouponChangeStatusRequest(loginMemberId, couponId, CouponEvent.of(couponEvent), meetingDate);
    }
}
