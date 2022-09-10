//package com.woowacourse.kkogkkog.support.fixture.domain;
//
//import com.woowacourse.kkogkkog.coupon2.domain.Coupon;
//import com.woowacourse.kkogkkog.coupon2.domain.CouponState;
//import com.woowacourse.kkogkkog.coupon2.domain.CouponStatus;
//import com.woowacourse.kkogkkog.coupon2.domain.CouponType;
//import com.woowacourse.kkogkkog.member.domain.Member;
//
//public enum CouponFixture {
//
//    COFFEE("멋진", "https://imageUrl.com", "COFFEE")
//    ;
//
//    private final String tag;
//    private final String description;
//    private final String couponType;
//
//    CouponFixture(String tag, String description, String couponType) {
//        this.tag = tag;
//        this.description = description;
//        this.couponType = couponType;
//    }
//
//    public Coupon getCoupon(Member sender, Member receiver) {
//        return new Coupon(
//            null, sender, receiver, tag, description, CouponType.valueOf(couponType), new CouponState(CouponStatus.READY, null));
//    }
//
//    public Coupon getCoupon(Member sender, Member receiver, String status) {
//        return new Coupon(
//            null, sender, receiver, tag, description, CouponType.valueOf(couponType), new CouponState(CouponStatus.valueOf(status), null));
//    }
//}
