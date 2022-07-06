package com.woowacourse.kkogkkog.application.dto;

import com.woowacourse.kkogkkog.domain.CouponTemplate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponTemplateResponse {

    private Long id;
    private String modifier;
    private String backgroundColor;
    private String couponType;

    public CouponTemplateResponse(Long id, String modifier, String backgroundColor, String couponType) {
        this.id = id;
        this.modifier = modifier;
        this.backgroundColor = backgroundColor;
        this.couponType = couponType;
    }

    public static CouponTemplateResponse of(CouponTemplate couponTemplate) {
        return new CouponTemplateResponse(
                couponTemplate.getId(),
                couponTemplate.getModifier(),
                couponTemplate.getBackgroundColor(),
                couponTemplate.getCouponType().name());
    }
}
