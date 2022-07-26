package com.woowacourse.kkogkkog.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.exception.InvalidRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("CouponStatus 클래스의")
class CouponStatusTest2 {

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

        @Test
        @DisplayName("DECLINE 이벤트를 받으면, 예외를 던진다.")
        void fail_decline() {
            CouponStatus currentStatus = CouponStatus.READY;
            CouponEvent event = CouponEvent.DECLINE;

            assertThatThrownBy(() -> currentStatus.handle(event))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("ACCEPT 이벤트를 받으면, 예외를 던진다.")
        void fail_accept() {
            CouponStatus currentStatus = CouponStatus.READY;
            CouponEvent event = CouponEvent.ACCEPT;

            assertThatThrownBy(() -> currentStatus.handle(event))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("FINISH 이벤트를 받으면, FINISHED 상태로 변경된다.")
        void success_finish() {
            CouponStatus currentStatus = CouponStatus.READY;
            CouponEvent event = CouponEvent.FINISH;

            CouponStatus actual = currentStatus.handle(event);
            CouponStatus expected = CouponStatus.FINISHED;

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("REQUESTED 상태는")
    class Requested {

        @Test
        @DisplayName("DECLINE 이벤트를 받으면, READY 를 반환한다.")
        void success_decline() {
            CouponStatus currentStatus = CouponStatus.REQUESTED;
            CouponEvent event = CouponEvent.DECLINE;

            CouponStatus actual = currentStatus.handle(event);
            CouponStatus expected = CouponStatus.READY;

            assertThat(actual).isEqualTo(expected);
        }

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
        void success_cancel() {
            CouponStatus currentStatus = CouponStatus.REQUESTED;
            CouponEvent event = CouponEvent.CANCEL;

            CouponStatus actual = currentStatus.handle(event);
            CouponStatus expected = CouponStatus.READY;

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("ACCEPT 이벤트를 받으면, ACCEPTED 를 반환한다.")
        void success_accept() {
            CouponStatus currentStatus = CouponStatus.REQUESTED;
            CouponEvent event = CouponEvent.ACCEPT;

            CouponStatus actual = currentStatus.handle(event);
            CouponStatus expected = CouponStatus.ACCEPTED;

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("FINISH 이벤트를 받으면, FINISHED 를 반환한다.")
        void success_finish() {
            CouponStatus currentStatus = CouponStatus.REQUESTED;
            CouponEvent event = CouponEvent.FINISH;

            CouponStatus actual = currentStatus.handle(event);
            CouponStatus expected = CouponStatus.FINISHED;

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("ACCEPTED 상태는")
    class Accepted {

        @Test
        @DisplayName("ACCEPTED 상태의 쿠폰은 FINISH 이벤트를 받으면 FINISHED 상태로 변경된다.")
        void acceptedChangesToFinishOnFinishedEvent() {
            CouponStatus currentStatus = CouponStatus.ACCEPTED;
            CouponEvent event = CouponEvent.FINISH;

            CouponStatus actual = currentStatus.handle(event);
            CouponStatus expected = CouponStatus.FINISHED;

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("FINISHED 상태는")
    class Finished {

        @Test
        @DisplayName("FINISHED 상태의 쿠폰은 FINISH 이벤트를 받으면 예외가 발생된다.")
        void finishedCanNotHandleFinishEvent() {
            CouponStatus currentStatus = CouponStatus.FINISHED;
            CouponEvent event = CouponEvent.FINISH;

            assertThatThrownBy(() -> currentStatus.handle(event))
                .isInstanceOf(InvalidRequestException.class);
        }
    }
}
