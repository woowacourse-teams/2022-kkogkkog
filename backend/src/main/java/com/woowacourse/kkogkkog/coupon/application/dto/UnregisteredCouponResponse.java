package com.woowacourse.kkogkkog.coupon.application.dto;

import com.woowacourse.kkogkkog.coupon.domain.UnregisteredCoupon;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UnregisteredCouponResponse {

    private Long id;
    private String couponCode;
    private CouponMemberResponse sender;
    private String couponTag;
    private String couponMessage;
    private String couponType;
    private Boolean deleted;
    private LocalDateTime createdTime;

    public UnregisteredCouponResponse(Long id, String couponCode, CouponMemberResponse sender,
                                      String couponTag, String couponMessage, String couponType,
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

    // todo: CouponResponse 동일하게 구현. getter 불편하여 리팩터링 필요!
    public static UnregisteredCouponResponse of(UnregisteredCoupon coupon) {
        Member sender = coupon.getSender();
        return new UnregisteredCouponResponse(
            coupon.getId(),
            coupon.getCouponCode(),
            new CouponMemberResponse(sender.getId(), sender.getNickname(), sender.getImageUrl()),
            coupon.getCouponTag(),
            coupon.getCouponMessage(),
            coupon.getCouponType().name(),
            coupon.isDeleted(),
            coupon.getCreatedTime());
    }
}
