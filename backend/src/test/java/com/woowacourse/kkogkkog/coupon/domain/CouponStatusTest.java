package com.woowacourse.kkogkkog.coupon.domain;

import static com.woowacourse.kkogkkog.coupon.domain.CouponEventType.ACCEPT;
import static com.woowacourse.kkogkkog.coupon.domain.CouponEventType.CANCEL;
import static com.woowacourse.kkogkkog.coupon.domain.CouponEventType.DECLINE;
import static com.woowacourse.kkogkkog.coupon.domain.CouponEventType.FINISH;
import static com.woowacourse.kkogkkog.coupon.domain.CouponStatus.ACCEPTED;
import static com.woowacourse.kkogkkog.coupon.domain.CouponStatus.FINISHED;
import static com.woowacourse.kkogkkog.coupon.domain.CouponStatus.READY;
import static com.woowacourse.kkogkkog.coupon.domain.CouponStatus.REQUESTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.common.exception.InvalidRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("CouponStatus 클래스의")
class CouponStatusTest {

    @Nested
    @DisplayName("상태가 READY일 때")
    class Ready {

        @Test
        @DisplayName("REQUEST 이벤트를 받으면, REQUESTED를 반환한다.")
        void success_request() {
            CouponStatus status = READY;

            CouponStatus actual = status.handle(CouponEventType.REQUEST);

            assertThat(actual).isEqualTo(REQUESTED);
        }

        @Test
        @DisplayName("FINISH 이벤트를 받으면, FINISHED를 반환한다.")
        void success_finish() {
            CouponStatus actual = READY.handle(FINISH);

            assertThat(actual).isEqualTo(FINISHED);
        }

        @Test
        @DisplayName("CANCEL 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_cancel() {
            assertThatThrownBy(() -> READY.handle(CANCEL))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("DECLINE 이벤틀르 받으면, 예외를 발생시킨다.")
        void fail_decline() {
            assertThatThrownBy(() -> READY.handle(DECLINE))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("ACCEPT 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_accept() {
            assertThatThrownBy(() -> READY.handle(ACCEPT))
                .isInstanceOf(InvalidRequestException.class);
        }
    }

    @Nested
    @DisplayName("상태가 REQUESTED일 때")
    class Requested {

        @Test
        @DisplayName("CANCEL 이벤트를 받으면, READY를 반환한다.")
        void success_cancel() {
            CouponStatus actual = REQUESTED.handle(CANCEL);

            assertThat(actual).isEqualTo(READY);
        }

        @Test
        @DisplayName("DECLINE 이벤트를 받으면, READY를 반환한다.")
        void success_decline() {
            CouponStatus actual = REQUESTED.handle(DECLINE);

            assertThat(actual).isEqualTo(READY);
        }

        @Test
        @DisplayName("ACCEPT 이벤트를 받으면, ACCEPTED를 반환한다.")
        void success_accept() {
            CouponStatus actual = REQUESTED.handle(ACCEPT);

            assertThat(actual).isEqualTo(ACCEPTED);
        }

        @Test
        @DisplayName("FINISH 이벤트를 받으면, FINISHED를 반환한다.")
        void success_finish() {
            CouponStatus actual = REQUESTED.handle(FINISH);

            assertThat(actual).isEqualTo(FINISHED);
        }

        @Test
        @DisplayName("REQUEST 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_request() {
            assertThatThrownBy(() -> REQUESTED.handle(CouponEventType.REQUEST))
                .isInstanceOf(InvalidRequestException.class);
        }
    }

    @Nested
    @DisplayName("상태가 ACCEPTED일 때")
    class Accepted {

        @Test
        @DisplayName("CANCEL 이벤트를 받으면, READY를 반환한다.")
        void success_cancel() {
            CouponStatus actual = ACCEPTED.handle(CANCEL);

            assertThat(actual).isEqualTo(READY);
        }

        @Test
        @DisplayName("FINISH 이벤트를 받으면, FINISHED를 반환한다.")
        void success_finish() {
            CouponStatus actual = ACCEPTED.handle(FINISH);

            assertThat(actual).isEqualTo(FINISHED);
        }

        @Test
        @DisplayName("REQUEST 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_request() {
            assertThatThrownBy(() -> ACCEPTED.handle(CouponEventType.REQUEST))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("DECLINE 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_decline() {
            assertThatThrownBy(() -> ACCEPTED.handle(DECLINE))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("ACCEPT 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_accept() {
            assertThatThrownBy(() -> ACCEPTED.handle(ACCEPT))
                .isInstanceOf(InvalidRequestException.class);
        }
    }

    @Nested
    @DisplayName("상태가 FINISHED일 때")
    class Finished {

        @Test
        @DisplayName("REQUEST 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_request() {
            assertThatThrownBy(() -> FINISHED.handle(CouponEventType.REQUEST))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("CANCEL 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_cancel() {
            assertThatThrownBy(() -> FINISHED.handle(CANCEL))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("DECLINE 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_decline() {
            assertThatThrownBy(() -> FINISHED.handle(DECLINE))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("ACCEPT 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_accept() {
            assertThatThrownBy(() -> FINISHED.handle(ACCEPT))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("FINISH 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_finish() {
            assertThatThrownBy(() -> FINISHED.handle(FINISH))
                .isInstanceOf(InvalidRequestException.class);
        }
    }
}
