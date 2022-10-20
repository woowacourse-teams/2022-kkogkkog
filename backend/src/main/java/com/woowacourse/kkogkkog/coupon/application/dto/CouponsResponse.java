package com.woowacourse.kkogkkog.coupon.application.dto;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponsResponse {

    private List<CouponResponse> data;
    private Boolean isNext;

    public CouponsResponse(List<CouponResponse> data, boolean isNext) {
        this.data = data;
        this.isNext = isNext;
    }

    public static CouponsResponse createSliceDto(final Slice<Coupon> coupons) {
        List<CouponResponse> responses = coupons.stream()
            .map(CouponResponse::of)
            .collect(Collectors.toList());

        return new CouponsResponse(responses, coupons.hasNext());
    }
}