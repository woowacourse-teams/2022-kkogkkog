package com.woowacourse.kkogkkog.coupon.domain;

import static com.woowacourse.kkogkkog.coupon.domain.CouponEventType.CANCEL;
import static com.woowacourse.kkogkkog.coupon.domain.CouponEventType.DECLINE;
import static com.woowacourse.kkogkkog.coupon.domain.CouponEventType.REQUEST;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class CouponEvent {

    private final CouponEventType type;
    private final LocalDateTime meetingDate;

    public CouponEvent(CouponEventType type, LocalDateTime meetingDate) {
        this.type = type;
        this.meetingDate = convertMeetingDate(type, meetingDate);
    }

    private LocalDateTime convertMeetingDate(CouponEventType type, LocalDateTime meetingDate) {
        if (!type.needsMeetingDate()) {
            return null;
        }
        if (meetingDate == null) {
            throw new IllegalArgumentException("예약 날짜 정보가 필요합니다.");
        }
        return meetingDate;
    }

    public void checkExecutable(boolean isSender, boolean isReceiver) {
        type.checkExecutable(isSender, isReceiver);
    }

    public boolean shouldUpdateMeetingDate() {
        return List.of(REQUEST, CANCEL, DECLINE).contains(type);
    }
}
