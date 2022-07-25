package com.woowacourse.kkogkkog.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.exception.InvalidRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("CouponStatus 클래스의")
class CouponStatusTest {

    @Nested
    @DisplayName("READY 상태는")
    class Ready {

        @Test
        @DisplayName("REQUEST 이벤트를 받으면, REQUESTED 를 반환한다.")
        void success_request() {
            CouponStatus currentStatus = CouponStatus.READY;
            CouponEvent event = CouponEvent.REQUEST;

            CouponStatus actual = currentStatus.handle(event);
            CouponStatus expected = CouponStatus.REQUESTED;

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("CANCEL 이벤트를 받으면, 예외를 던진다.")
        void fail_cancel() {
            CouponStatus currentStatus = CouponStatus.READY;
            CouponEvent event = CouponEvent.CANCEL;

            assertThatThrownBy(() -> currentStatus.handle(event))
                    .isInstanceOf(InvalidRequestException.class);
        }
    }

    @Nested
    @DisplayName("REQUESTED 상태는")
    class Requested {

        @Test
        @DisplayName("REQUEST 이벤트를 받으면, 예외를 던진다.")
        void fail_request() {
            CouponStatus currentStatus = CouponStatus.REQUESTED;
            CouponEvent event = CouponEvent.REQUEST;

            assertThatThrownBy(() -> currentStatus.handle(event))
                    .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("CANCEL 이벤트를 받으면, READY 를 반환한다.")
        void fail_cancel() {
            CouponStatus currentStatus = CouponStatus.REQUESTED;
            CouponEvent event = CouponEvent.CANCEL;

            CouponStatus actual = currentStatus.handle(event);
            CouponStatus expected = CouponStatus.READY;

            assertThat(actual).isEqualTo(expected);
        }
    }
}
