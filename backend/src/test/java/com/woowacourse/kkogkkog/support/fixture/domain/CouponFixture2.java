package com.woowacourse.kkogkkog.support.fixture.domain;

import com.woowacourse.kkogkkog.coupon2.domain.Coupon;
import com.woowacourse.kkogkkog.coupon2.domain.CouponState;
import com.woowacourse.kkogkkog.coupon2.domain.CouponStatus;
import com.woowacourse.kkogkkog.coupon2.domain.CouponType;
import com.woowacourse.kkogkkog.member.domain.Member;

public enum CouponFixture2 {

    COFFEE("멋진", "https://imageUrl.com", "COFFEE")
    ;

    private final String tag;
    private final String imageUrl;
    private final String couponType;

    CouponFixture2(String tag, String imageUrl, String couponType) {
        this.tag = tag;
        this.imageUrl = imageUrl;
        this.couponType = couponType;
    }

    public Coupon getCoupon(Member sender, Member receiver) {
        return new Coupon(
            sender, receiver, tag, imageUrl, CouponType.valueOf(couponType), new CouponState(CouponStatus.READY, null));
    }
}
