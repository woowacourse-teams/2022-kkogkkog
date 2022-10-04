package com.woowacourse.kkogkkog.coupon.application.dto;

import com.woowacourse.kkogkkog.coupon.domain.UnregisteredCoupon;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UnregisteredCouponDetailResponse {

    private Long id;
    private String couponCode;
    private CouponMemberResponse sender;
    private String couponTag;
    private String couponMessage;
    private String couponType;
    private Boolean deleted;
    private LocalDateTime createdTime;

    public UnregisteredCouponDetailResponse(Long id, String couponCode, CouponMemberResponse sender, String couponTag,
                                            String couponMessage, String couponType,
                                            Boolean deleted, LocalDateTime createdTime) {
        this.id = id;
        this.couponCode = couponCode;
        this.sender = sender;
        this.couponTag = couponTag;
        this.couponMessage = couponMessage;
        this.couponType = couponType;
        this.deleted = deleted;
        this.createdTime = createdTime;
    }

    public static UnregisteredCouponDetailResponse of(final UnregisteredCoupon coupon) {
        return new UnregisteredCouponDetailResponse(
            coupon.getId(),
            coupon.getCouponCode(),
            new CouponMemberResponse(
                coupon.getSender().getId(),
                coupon.getSender().getNickname(),
                coupon.getSender().getImageUrl()
            ),
            coupon.getCouponTag(),
            coupon.getCouponMessage(),
            coupon.getCouponType().name(),
            coupon.isDeleted(),
            coupon.getCreatedTime()
        );
    }
}
