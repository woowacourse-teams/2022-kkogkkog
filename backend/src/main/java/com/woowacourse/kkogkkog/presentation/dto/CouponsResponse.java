package com.woowacourse.kkogkkog.presentation.dto;

import com.woowacourse.kkogkkog.application.dto.CouponResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponsResponse {

    private List<CouponResponse> received;
    private List<CouponResponse> sent;

    public CouponsResponse(List<CouponResponse> received,
                           List<CouponResponse> sent) {
        this.received = received;
        this.sent = sent;
    }
}
