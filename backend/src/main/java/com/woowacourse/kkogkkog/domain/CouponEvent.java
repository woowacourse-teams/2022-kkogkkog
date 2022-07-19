package com.woowacourse.kkogkkog.domain;

import com.woowacourse.kkogkkog.exception.ForbiddenException;
import java.util.function.BiConsumer;

public enum CouponEvent {

    REQUEST(CouponEvent::canRequest),
    ;

    private final BiConsumer<Boolean, Boolean> canChange;

    CouponEvent(BiConsumer<Boolean, Boolean> canChange) {
        this.canChange = canChange;
    }

    public void checkExecutable(boolean isSender, boolean isReceiver) {
        canChange.accept(isSender, isReceiver);
    }

    private static void canRequest(boolean isSender, boolean isReceiver) {
        if (!isReceiver) {
            throw new ForbiddenException("쿠폰을 받은 사람만 쿠폰을 사용할 수 있습니다.");
        }
    }
}
