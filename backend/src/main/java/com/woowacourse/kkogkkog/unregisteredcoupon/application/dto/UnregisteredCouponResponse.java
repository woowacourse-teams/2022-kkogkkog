package com.woowacourse.kkogkkog.unregisteredcoupon.application.dto;

import static com.woowacourse.kkogkkog.unregisteredcoupon.domain.UnregisteredCouponStatus.REGISTERED;

import com.woowacourse.kkogkkog.coupon.application.dto.CouponMemberResponse;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.unregisteredcoupon.domain.CouponUnregisteredCoupon;
import com.woowacourse.kkogkkog.unregisteredcoupon.domain.UnregisteredCoupon;
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
    private CouponMemberResponse receiver;
    private Long couponId;
    private String couponTag;
    private String couponMessage;
    private String couponType;
    private String unregisteredCouponStatus;
    private LocalDateTime createdTime;

    public UnregisteredCouponResponse(Long id, String couponCode, CouponMemberResponse sender, CouponMemberResponse receiver,
                                      Long couponId, String couponTag, String couponMessage, String couponType,
                                      String unregisteredCouponStatus, LocalDateTime createdTime) {
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

    public static UnregisteredCouponResponse of(UnregisteredCoupon unregisteredCoupon) {
        return toResponse(unregisteredCoupon, null, null);
    }

    public static UnregisteredCouponResponse of(CouponUnregisteredCoupon couponUnregisteredCoupon) {
        UnregisteredCoupon unregisteredCoupon = couponUnregisteredCoupon.getUnregisteredCoupon();
        Coupon coupon = couponUnregisteredCoupon.getCoupon();
        return toResponse(unregisteredCoupon, CouponMemberResponse.of(coupon.getReceiver()), coupon.getId());
    }

    private static UnregisteredCouponResponse toResponse(UnregisteredCoupon unregisteredCoupon, CouponMemberResponse receiver, Long couponId) {
        Member sender = unregisteredCoupon.getSender();
        return new UnregisteredCouponResponse(
            unregisteredCoupon.getId(),
            unregisteredCoupon.getCouponCode(),
            CouponMemberResponse.of(sender),
            receiver,
            couponId,
            unregisteredCoupon.getCouponTag(),
            unregisteredCoupon.getCouponMessage(),
            unregisteredCoupon.getCouponType().name(),
            unregisteredCoupon.getUnregisteredCouponStatus().name(),
            unregisteredCoupon.getCreatedTime());
    }
}
