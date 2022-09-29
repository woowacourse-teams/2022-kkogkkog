package com.woowacourse.kkogkkog.support.fixture.domain;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponHistory;
import com.woowacourse.kkogkkog.member.domain.Member;

public enum CouponHistoryFixture {

    INIT(CouponEventFixture.INIT, "생성 메시지"),
    REQUEST(CouponEventFixture.REQUEST, "요청 메시지"),
    CANCEL(CouponEventFixture.CANCEL, "취소 메시지"),
    DECLINE(CouponEventFixture.DECLINE, "거절 메시지"),
    ACCEPT(CouponEventFixture.ACCEPT, "허락 메시지"),
    FINISH(CouponEventFixture.FINISH, "완료 메시지");

    private final CouponEventFixture couponEvent;
    private final String message;

    CouponHistoryFixture(final CouponEventFixture couponEvent, final String message) {
        this.couponEvent = couponEvent;
        this.message = message;
    }

    public CouponHistory getCouponHistory(Member member, Coupon coupon) {
        return CouponHistory.of(member, coupon, couponEvent.getCouponEvent(), message);
    }
}
