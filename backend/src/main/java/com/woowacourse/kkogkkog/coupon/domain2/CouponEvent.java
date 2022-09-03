package com.woowacourse.kkogkkog.coupon.domain2;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CouponEvent {

    private final CouponEventType type;
    private final LocalDateTime meetingDate;

    public CouponEvent(CouponEventType type, LocalDateTime meetingDate) {
        this.type = type;
        this.meetingDate = toValidMeetingDate(type, meetingDate);
    }

    private LocalDateTime toValidMeetingDate(CouponEventType type, LocalDateTime meetingDate) {
        if (!type.needsMeetingDate()) {
            return null;
        }
        if (meetingDate == null) {
            throw new IllegalArgumentException("예약 날짜 정보가 누락되었습니다.");
        }
        return meetingDate;
    }

    public void checkExecutable(boolean isSender, boolean isReceiver) {
        type.checkExecutable(isSender, isReceiver);
    }

    public boolean shouldUpdateMeetingDate() {
        return type.isUpdateMeetingDateType();
    }
}
