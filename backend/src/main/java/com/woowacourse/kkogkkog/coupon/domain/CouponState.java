package com.woowacourse.kkogkkog.coupon.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponState {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponStatus couponStatus;

    @Column
    private LocalDateTime meetingDate;

    public CouponState(CouponStatus couponStatus, LocalDateTime meetingDate) {
        this.couponStatus = couponStatus;
        this.meetingDate = meetingDate;
    }

    public static CouponState ofReady() {
        return new CouponState(CouponStatus.READY, null);
    }

    public void changeStatus(CouponEvent couponEvent) {
        this.couponStatus = couponStatus.handle(couponEvent.getType());
        updateMeetingDate(couponEvent);
    }

    private void updateMeetingDate(CouponEvent couponEvent) {
        if (couponEvent.shouldUpdateMeetingDate()) {
            this.meetingDate = couponEvent.getMeetingDate();
        }
    }
}
