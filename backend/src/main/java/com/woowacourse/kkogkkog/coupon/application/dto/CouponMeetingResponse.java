package com.woowacourse.kkogkkog.coupon.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponMeetingResponse {

    private LocalDateTime meetingDate;
    private List<CouponMeetingData> coupons;

    public CouponMeetingResponse(final LocalDateTime meetingDate,
                                 final List<CouponMeetingData> couponMeetingData) {
        this.meetingDate = meetingDate;
        this.coupons = couponMeetingData;
    }

    public static CouponMeetingResponse of(final LocalDateTime meetingDate,
                                           final List<CouponMeetingData> value) {
        return new CouponMeetingResponse(meetingDate, value);
    }
}
