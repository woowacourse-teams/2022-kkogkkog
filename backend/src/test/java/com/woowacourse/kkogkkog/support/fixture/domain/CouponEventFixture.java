package com.woowacourse.kkogkkog.support.fixture.domain;

import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.coupon.domain.CouponEventType;
import java.time.LocalDateTime;

public enum CouponEventFixture {

    INIT(CouponEventType.INIT, null),
    REQUEST(CouponEventType.REQUEST, LocalDateTime.of(2023, 9, 7, 0, 0, 0)),
    CANCEL(CouponEventType.CANCEL, null),
    DECLINE(CouponEventType.DECLINE, null),
    ACCEPT(CouponEventType.ACCEPT, null),
    FINISH(CouponEventType.FINISH, null);

    private final CouponEventType eventType;
    private final LocalDateTime meetingDate;

    CouponEventFixture(final CouponEventType eventType, final LocalDateTime meetingDate) {
        this.eventType = eventType;
        this.meetingDate = meetingDate;
    }

    public CouponEvent getCouponEvent() {
        return new CouponEvent(eventType, meetingDate);
    }
}
