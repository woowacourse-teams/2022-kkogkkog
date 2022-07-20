package com.woowacourse.kkogkkog.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.exception.InvalidRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponStatusTest {

    @Test
    @DisplayName("READY 상태의 쿠폰은 REQUEST 이벤트를 받으면 REQUESTED 상태로 변경된다.")
    void readyChangesToRequestedOnRequestEvent() {
        CouponStatus currentStatus = CouponStatus.READY;
        CouponEvent event = CouponEvent.REQUEST;

        CouponStatus actual = currentStatus.handle(event);
        CouponStatus expected = CouponStatus.REQUESTED;

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("REQUESTED 상태의 쿠폰은 REQUEST 이벤트를 받으면 예외가 발생된다.")
    void requestedCanNotHandleRequestEvent() {
        CouponStatus currentStatus = CouponStatus.REQUESTED;
        CouponEvent event = CouponEvent.REQUEST;

        assertThatThrownBy(() -> currentStatus.handle(event))
                .isInstanceOf(InvalidRequestException.class);
    }
}
