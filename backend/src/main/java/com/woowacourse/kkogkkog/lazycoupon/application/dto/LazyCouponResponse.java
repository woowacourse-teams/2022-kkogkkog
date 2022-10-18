package com.woowacourse.kkogkkog.lazycoupon.application.dto;

import static com.woowacourse.kkogkkog.lazycoupon.domain.LazyCouponStatus.REGISTERED;

import com.woowacourse.kkogkkog.coupon.application.dto.CouponMemberResponse;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.lazycoupon.domain.CouponLazyCoupon;
import com.woowacourse.kkogkkog.lazycoupon.domain.LazyCoupon;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LazyCouponResponse {

    private Long id;
    private String couponCode;
    private CouponMemberResponse sender;
    private CouponMemberResponse receiver;
    private Long couponId;
    private String couponTag;
    private String couponMessage;
    private String couponType;
    private String lazyCouponStatus;
    private LocalDateTime createdTime;

    public LazyCouponResponse(Long id, String couponCode, CouponMemberResponse sender, CouponMemberResponse receiver,
                              Long couponId, String couponTag, String couponMessage, String couponType,
                              String lazyCouponStatus, LocalDateTime createdTime) {
        this.id = id;
        this.couponCode = couponCode;
        this.sender = sender;
        this.receiver = receiver;
        this.couponId = couponId;
        this.couponTag = couponTag;
        this.couponMessage = couponMessage;
        this.couponType = couponType;
        this.lazyCouponStatus = lazyCouponStatus;
        this.createdTime = createdTime;
    }

    public static LazyCouponResponse of(CouponLazyCoupon couponLazyCoupon) {
        LazyCoupon lazyCoupon = couponLazyCoupon.getLazyCoupon();
        Coupon coupon = couponLazyCoupon.getCoupon();
        if (REGISTERED.equals(lazyCoupon.getLazyCouponStatus())) {
            return toResponse(lazyCoupon, CouponMemberResponse.of(coupon.getReceiver()), coupon.getId());
        }
        return toResponse(lazyCoupon, null, null);
    }

    private static LazyCouponResponse toResponse(LazyCoupon lazyCoupon, CouponMemberResponse receiver, Long couponId) {
        Member sender = lazyCoupon.getSender();
        return new LazyCouponResponse(
            lazyCoupon.getId(),
            lazyCoupon.getCouponCode(),
            CouponMemberResponse.of(sender),
            receiver,
            couponId,
            lazyCoupon.getCouponTag(),
            lazyCoupon.getCouponMessage(),
            lazyCoupon.getCouponType().name(),
            lazyCoupon.getLazyCouponStatus().name(),
            lazyCoupon.getCreatedTime());
    }
}
