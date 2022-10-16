package com.woowacourse.kkogkkog.support.fixture.domain;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponState;
import com.woowacourse.kkogkkog.coupon.domain.CouponStatus;
import com.woowacourse.kkogkkog.coupon.domain.CouponType;
import com.woowacourse.kkogkkog.unregisteredcoupon.UnregisteredCoupon;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.time.LocalDateTime;

public enum CouponFixture {

    COFFEE("고마워요!", "커피쿠폰입니다.", CouponType.COFFEE),

    // State 에 대한 Coupon
    ACCEPTED_COUPON("쿠폰 테그", "쿠폰 메시지");

    private String couponTag;
    private String couponMessage;
    private CouponType couponType;

    CouponFixture(final String couponTag, final String couponMessage) {
        this.couponTag = couponTag;
        this.couponMessage = couponMessage;
    }

    CouponFixture(final String couponTag, final String couponMessage, final CouponType couponType) {
        this.couponTag = couponTag;
        this.couponMessage = couponMessage;
        this.couponType = couponType;
    }

    public Coupon getCoupon(Member sender, Member receiver) {
        return new Coupon(sender, receiver, couponTag, couponMessage, couponType);
    }

    public Coupon getRequestedCoupon(Member sender, Member receiver) {
        return new Coupon(null, sender, receiver, couponTag, couponMessage, couponType,
            new CouponState(CouponStatus.REQUESTED, LocalDateTime.now().plusDays(7)));
    }

    public Coupon getCoupon(Member sender,
                            Member receiver,
                            CouponType couponType,
                            CouponState couponState) {
        return new Coupon(null, sender, receiver, couponMessage, couponTag, couponType,
            couponState);
    }

    public UnregisteredCoupon getUnregisteredCoupon(Member sender) {
        return UnregisteredCoupon.of(sender, couponTag, couponMessage, couponType);
    }

    public Coupon getCoupon(Member sender, Member receiver, CouponState couponState) {
        return new Coupon(null, sender, receiver, couponMessage, couponTag, couponType, couponState);
    }
}
