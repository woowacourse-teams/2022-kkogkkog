package com.woowacourse.kkogkkog.coupon.presentation.dto;

import com.woowacourse.kkogkkog.coupon.application.dto.CouponMeetingResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponMeetingsResponse {

    private List<CouponMeetingResponse> data;

    public CouponMeetingsResponse(final List<CouponMeetingResponse> data) {
        this.data = data;
    }
}
