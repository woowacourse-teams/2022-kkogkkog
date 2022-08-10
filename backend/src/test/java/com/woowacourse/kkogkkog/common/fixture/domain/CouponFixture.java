package com.woowacourse.kkogkkog.common.fixture.domain;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponStatus;
import com.woowacourse.kkogkkog.coupon.domain.CouponType;
import com.woowacourse.kkogkkog.domain.Member;

public enum CouponFixture {

    COFFEE("멋진", "https://imageUrl.com", "COFFEE")
    ;

    private final String tag;
    private final String imageUrl;
    private final String couponType;

    CouponFixture(String tag, String imageUrl, String couponType) {
        this.tag = tag;
        this.imageUrl = imageUrl;
        this.couponType = couponType;
    }

    public Coupon getCoupon(Member sender, Member receiver) {
        return new Coupon(
            sender, receiver, tag, imageUrl, CouponType.valueOf(couponType), CouponStatus.READY);
    }
}
