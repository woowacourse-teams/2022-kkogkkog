package com.woowacourse.kkogkkog.support.fixture.domain;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponType;
import com.woowacourse.kkogkkog.member.domain.Member;

public enum CouponFixture {

    COFFEE("고마워요!", "커피쿠폰입니다.", CouponType.COFFEE);

    private final String hashTag;
    private final String description;
    private final CouponType couponType;

    CouponFixture(String hashTag, String description, CouponType couponType) {
        this.hashTag = hashTag;
        this.description = description;
        this.couponType = couponType;
    }

    public Coupon getCoupon(Member sender, Member receiver) {
        return new Coupon(sender, receiver, hashTag, description, couponType);
    }
}
