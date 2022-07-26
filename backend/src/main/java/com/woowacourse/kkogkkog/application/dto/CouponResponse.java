package com.woowacourse.kkogkkog.application.dto;

import com.woowacourse.kkogkkog.domain.Coupon;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponResponse {

    private Long id;
    private CouponMemberResponse sender;
    private CouponMemberResponse receiver;
    private String modifier;
    private String message;
    private String backgroundColor;
    private String couponType;
    private String couponStatus;

    public CouponResponse(Long id, CouponMemberResponse sender,
                          CouponMemberResponse receiver, String modifier, String message,
                          String backgroundColor, String couponType, String couponStatus) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.modifier = modifier;
        this.message = message;
        this.backgroundColor = backgroundColor;
        this.couponType = couponType;
        this.couponStatus = couponStatus;
    }

    public static CouponResponse of(Coupon coupon) {
        return new CouponResponse(
            coupon.getId(),
            CouponMemberResponse.of(coupon.getSender()),
            CouponMemberResponse.of(coupon.getReceiver()),
            coupon.getModifier(),
            coupon.getMessage(),
            coupon.getBackgroundColor(),
            coupon.getCouponType().name(),
            coupon.getCouponStatus().name());
    }
}
