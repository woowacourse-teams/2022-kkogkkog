package com.woowacourse.kkogkkog.coupon2.domain;

import static com.woowacourse.kkogkkog.coupon2.domain.CouponEventType.ACCEPT;
import static com.woowacourse.kkogkkog.coupon2.domain.CouponEventType.CANCEL;
import static com.woowacourse.kkogkkog.coupon2.domain.CouponEventType.DECLINE;
import static com.woowacourse.kkogkkog.coupon2.domain.CouponEventType.FINISH;
import static com.woowacourse.kkogkkog.coupon2.domain.CouponStatus.ACCEPTED;
import static com.woowacourse.kkogkkog.coupon2.domain.CouponStatus.FINISHED;
import static com.woowacourse.kkogkkog.coupon2.domain.CouponStatus.READY;
import static com.woowacourse.kkogkkog.coupon2.domain.CouponStatus.REQUESTED;
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
            CouponStatus status = READY;

            CouponStatus actual = status.handle(FINISH);

            assertThat(actual).isEqualTo(FINISHED);
        }

        @Test
        @DisplayName("CANCEL 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_cancel() {
            CouponStatus status = READY;

            assertThatThrownBy(() -> status.handle(CANCEL))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("DECLINE 이벤틀르 받으면, 예외를 발생시킨다.")
        void fail_decline() {
            CouponStatus status = READY;

            assertThatThrownBy(() -> status.handle(DECLINE))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("ACCEPT 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_accept() {
            CouponStatus status = READY;

            assertThatThrownBy(() -> status.handle(ACCEPT))
                .isInstanceOf(InvalidRequestException.class);
        }
    }

    @Nested
    @DisplayName("상태가 REQUESTED일 때")
    class Requested {

        @Test
        @DisplayName("CANCEL 이벤트를 받으면, READY를 반환한다.")
        void success_cancel() {
            CouponStatus status = REQUESTED;

            CouponStatus actual = status.handle(CANCEL);

            assertThat(actual).isEqualTo(READY);
        }

        @Test
        @DisplayName("DECLINE 이벤트를 받으면, READY를 반환한다.")
        void success_decline() {
            CouponStatus status = REQUESTED;

            CouponStatus actual = status.handle(DECLINE);

            assertThat(actual).isEqualTo(READY);
        }

        @Test
        @DisplayName("ACCEPT 이벤트를 받으면, ACCEPTED를 반환한다.")
        void success_accept() {
            CouponStatus status = REQUESTED;

            CouponStatus actual = status.handle(ACCEPT);

            assertThat(actual).isEqualTo(ACCEPTED);
        }

        @Test
        @DisplayName("FINISH 이벤트를 받으면, FINISHED를 반환한다.")
        void success_finish() {
            CouponStatus status = REQUESTED;

            CouponStatus actual = status.handle(FINISH);

            assertThat(actual).isEqualTo(FINISHED);
        }

        @Test
        @DisplayName("REQUEST 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_request() {
            CouponStatus status = REQUESTED;

            assertThatThrownBy(() -> status.handle(CouponEventType.REQUEST))
                .isInstanceOf(InvalidRequestException.class);
        }
    }

    @Nested
    @DisplayName("상태가 ACCEPTED일 때")
    class Accepted {

        @Test
        @DisplayName("CANCEL 이벤트를 받으면, READY를 반환한다.")
        void success_cancel() {
            CouponStatus status = ACCEPTED;

            CouponStatus actual = status.handle(CANCEL);

            assertThat(actual).isEqualTo(READY);
        }

        @Test
        @DisplayName("FINISH 이벤트를 받으면, FINISHED를 반환한다.")
        void success_finish() {
            CouponStatus status = ACCEPTED;

            CouponStatus actual = status.handle(FINISH);

            assertThat(actual).isEqualTo(FINISHED);
        }

        @Test
        @DisplayName("REQUEST 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_request() {
            CouponStatus status = ACCEPTED;

            assertThatThrownBy(() -> status.handle(CouponEventType.REQUEST))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("DECLINE 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_decline() {
            CouponStatus status = ACCEPTED;

            assertThatThrownBy(() -> status.handle(DECLINE))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("ACCEPT 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_accept() {
            CouponStatus status = ACCEPTED;

            assertThatThrownBy(() -> status.handle(ACCEPT))
                .isInstanceOf(InvalidRequestException.class);
        }
    }

    @Nested
    @DisplayName("상태가 FINISHED일 때")
    class Finished {

        @Test
        @DisplayName("REQUEST 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_request() {
            CouponStatus status = FINISHED;

            assertThatThrownBy(() -> status.handle(CouponEventType.REQUEST))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("CANCEL 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_cancel() {
            CouponStatus status = FINISHED;

            assertThatThrownBy(() -> status.handle(CANCEL))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("DECLINE 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_decline() {
            CouponStatus status = FINISHED;

            assertThatThrownBy(() -> status.handle(DECLINE))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("ACCEPT 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_accept() {
            CouponStatus status = FINISHED;

            assertThatThrownBy(() -> status.handle(ACCEPT))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("FINISH 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_finish() {
            CouponStatus status = FINISHED;

            assertThatThrownBy(() -> status.handle(FINISH))
                .isInstanceOf(InvalidRequestException.class);
        }
    }
}
