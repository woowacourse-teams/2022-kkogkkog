package com.woowacourse.kkogkkog.application.dto;

import com.woowacourse.kkogkkog.domain.Coupon;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponResponse {

    private Long id;
    private String senderName;
    private String receiverName;
    private String backgroundColor;
    private String modifier;
    private String message;
    private String couponType;
    private String couponStatus;

    public CouponResponse(Long id, String senderName, String receiverName, String backgroundColor, String modifier,
                          String message, String couponType, String couponStatus) {
        this.id = id;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.backgroundColor = backgroundColor;
        this.modifier = modifier;
        this.message = message;
        this.couponType = couponType;
        this.couponStatus = couponStatus;
    }

    public static CouponResponse of(Coupon coupon) {
        return new CouponResponse(coupon.getId(), coupon.getSender().getName(), coupon.getReceiver().getName(),
                coupon.getBackgroundColor(), coupon.getModifier(), coupon.getMessage(),
                coupon.getCouponType().getValue(), coupon.getCouponStatus().name());
    }
}
