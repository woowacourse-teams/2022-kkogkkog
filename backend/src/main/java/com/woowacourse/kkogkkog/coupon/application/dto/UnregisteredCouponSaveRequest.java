package com.woowacourse.kkogkkog.coupon.application.dto;

import com.woowacourse.kkogkkog.coupon.domain.CouponType;
import com.woowacourse.kkogkkog.coupon.domain.UnregisteredCoupon;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class UnregisteredCouponSaveRequest {

    private final Long senderId;
    private final Integer quantity;
    private final String couponTag;
    private final String couponMessage;
    private final String couponType;

    public UnregisteredCouponSaveRequest(Long senderId, Integer quantity, String couponTag,
                                         String couponMessage, String couponType) {
        this.senderId = senderId;
        this.quantity = quantity;
        this.couponTag = couponTag;
        this.couponMessage = couponMessage;
        this.couponType = couponType;
    }

    public List<UnregisteredCoupon> toEntities(Member sender) {
        List<UnregisteredCoupon> unregisteredCoupons = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            unregisteredCoupons.add(getUnregisteredCoupon(sender));
        }
        return unregisteredCoupons;
    }

    private UnregisteredCoupon getUnregisteredCoupon(Member sender) {
        return UnregisteredCoupon.of(sender, couponTag, couponMessage,
            CouponType.valueOf(couponType));
    }
}
