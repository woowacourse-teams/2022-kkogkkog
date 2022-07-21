package com.woowacourse.kkogkkog.domain;

import com.woowacourse.kkogkkog.exception.ForbiddenException;
import com.woowacourse.kkogkkog.exception.InvalidRequestException;
import java.util.function.BiConsumer;

public enum CouponEvent {

    REQUEST(CouponEvent::canRequest),
    CANCEL(CouponEvent::canCancel),
    DECLINE(CouponEvent::canDecline),
    ;

    private final BiConsumer<Boolean, Boolean> canChange;

    CouponEvent(BiConsumer<Boolean, Boolean> canChange) {
        this.canChange = canChange;
    }

    public static CouponEvent of(String value) {
        try {
            return CouponEvent.valueOf(value);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidRequestException("처리할 수 없는 요청입니다.");
        }
    }

    public void checkExecutable(boolean isSender, boolean isReceiver) {
        canChange.accept(isSender, isReceiver);
    }

    private static void canRequest(boolean isSender, boolean isReceiver) {
        if (!isReceiver) {
            throw new ForbiddenException("쿠폰을 받은 사람만 사용할 수 있습니다.");
        }
    }

    private static void canCancel(boolean isSender, boolean isReceiver) {
        if (!isReceiver) {
            throw new ForbiddenException("쿠폰을 받은 사람만 사용 요청을 취소할 수 있습니다.");
        }
    }

    private static void canDecline(boolean isSender, boolean isReceiver) {
        if (!isSender) {
            throw new ForbiddenException("쿠폰을 보낸 사람만 사용 요청을 거절할 수 있습니다.");
        }
    }
}
