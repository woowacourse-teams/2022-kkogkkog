package com.woowacourse.kkogkkog.coupon.application.dto;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.UnregisteredCoupon;
import com.woowacourse.kkogkkog.member.domain.Member;
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
    private CouponMemberResponse receiver;
    private Long couponId;
    private String couponTag;
    private String couponMessage;
    private String couponType;
    private String unregisteredCouponStatus;
    private LocalDateTime createdTime;

    public UnregisteredCouponDetailResponse(Long id, String couponCode, CouponMemberResponse sender,
                                            CouponMemberResponse receiver, Long couponId, String couponTag, String couponMessage,
                                            String couponType, String unregisteredCouponStatus,
                                            LocalDateTime createdTime) {
        this.id = id;
        this.couponCode = couponCode;
        this.sender = sender;
        this.receiver = receiver;
        this.couponId = couponId;
        this.couponTag = couponTag;
        this.couponMessage = couponMessage;
        this.couponType = couponType;
        this.unregisteredCouponStatus = unregisteredCouponStatus;
        this.createdTime = createdTime;
    }

    public static UnregisteredCouponDetailResponse of(UnregisteredCoupon unregisteredCoupon) {
        Coupon coupon = unregisteredCoupon.getCoupon();
        Member sender = unregisteredCoupon.getSender();
//        Member receiver = coupon.getReceiver();

        return new UnregisteredCouponDetailResponse(
            unregisteredCoupon.getId(),
            unregisteredCoupon.getCouponCode(),
            CouponMemberResponse.of(sender),
            null,
            null,
            unregisteredCoupon.getCouponTag(),
            unregisteredCoupon.getCouponMessage(),
            unregisteredCoupon.getCouponType().name(),
            unregisteredCoupon.getUnregisteredCouponStatus().name(),
            unregisteredCoupon.getCreatedTime()
        );
    }
}
