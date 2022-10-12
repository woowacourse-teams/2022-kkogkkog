package com.woowacourse.kkogkkog.coupon.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AcceptedCouponResponse {

    private LocalDateTime meetingDate;
    private List<CouponMeetingData> coupons;

    public AcceptedCouponResponse(final LocalDateTime meetingDate,
                                  final List<CouponMeetingData> couponMeetingData) {
        this.meetingDate = meetingDate;
        this.coupons = couponMeetingData;
    }

    public static AcceptedCouponResponse of(final LocalDateTime meetingDate,
                                            final List<CouponMeetingData> value) {
        return new AcceptedCouponResponse(meetingDate, value);
    }
}
