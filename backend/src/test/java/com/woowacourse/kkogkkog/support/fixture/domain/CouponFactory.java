package com.woowacourse.kkogkkog.support.fixture.domain;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponState;
import com.woowacourse.kkogkkog.coupon.domain.CouponStatus;
import com.woowacourse.kkogkkog.coupon.domain.CouponType;
import com.woowacourse.kkogkkog.lazycoupon.domain.CouponLazyCoupon;
import com.woowacourse.kkogkkog.lazycoupon.domain.LazyCoupon;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.time.LocalDateTime;

public class CouponFactory {

    private static final String COUPON_TAG = "고마워요";
    private static final String COUPON_MESSAGE = "커피쿠폰 메시지";
    private static final CouponType COUPON_TYPE = CouponType.COFFEE;

    public static Coupon createCoupon(Member sender, Member receiver) {
        return new Coupon(sender, receiver, COUPON_TAG, COUPON_MESSAGE, COUPON_TYPE);
    }

    public static Coupon createCoupon(Member sender, Member receiver, CouponState couponState) {
        return new Coupon(null, sender, receiver, COUPON_MESSAGE, COUPON_TAG, COUPON_TYPE, couponState);
    }

    public static Coupon createRequestedCoupon(Member sender, Member receiver) {
        return new Coupon(null, sender, receiver, COUPON_TAG, COUPON_MESSAGE, COUPON_TYPE,
            new CouponState(CouponStatus.REQUESTED, LocalDateTime.now().plusDays(7)));
    }

    public static CouponLazyCoupon createCouponLazyCoupon(Member sender) {
        return new CouponLazyCoupon(null, LazyCoupon.of(sender, COUPON_TAG, COUPON_MESSAGE, COUPON_TYPE));
    }
}
