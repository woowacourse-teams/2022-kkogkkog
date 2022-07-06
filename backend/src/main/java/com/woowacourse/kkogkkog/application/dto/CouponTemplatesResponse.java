package com.woowacourse.kkogkkog.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponTemplatesResponse {

    private List<CouponTemplateResponse> data;

    public CouponTemplatesResponse(List<CouponTemplateResponse> data) {
        this.data = data;
    }
}
