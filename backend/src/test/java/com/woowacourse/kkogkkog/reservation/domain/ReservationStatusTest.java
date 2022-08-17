package com.woowacourse.kkogkkog.reservation.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.common.exception.InvalidRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("ReservationStatus 클래스의")
class ReservationStatusTest {

    @Nested
    @DisplayName("상태가 IN_PROGRESS일 때 ")
    class progress {

        @Test
        @DisplayName("CANCEL 이벤트를 받으면, CANCELED을 반환한다.")
        void success_cancel() {
            ReservationStatus status = ReservationStatus.IN_PROGRESS;

            ReservationStatus actual = status.handle(CouponEvent.CANCEL);

            assertThat(actual).isEqualTo(ReservationStatus.CANCELED);
        }

        @Test
        @DisplayName("DECLINE 이벤트를 받으면, CANCELED을 반환한다.")
        void success_decline() {
            ReservationStatus status = ReservationStatus.IN_PROGRESS;

            ReservationStatus actual = status.handle(CouponEvent.DECLINE);

            assertThat(actual).isEqualTo(ReservationStatus.CANCELED);
        }

        @Test
        @DisplayName("ACCEPT 이벤트를 받으면, IN_PROGRESS을 반환한다.")
        void fail_approve() {
            ReservationStatus status = ReservationStatus.IN_PROGRESS;

            ReservationStatus actual = status.handle(CouponEvent.ACCEPT);

            assertThat(actual).isEqualTo(ReservationStatus.IN_PROGRESS);
        }

        @Test
        @DisplayName("FINISH 이벤트를 받으면, DONE을 반환한다.")
        void success_finish() {
            ReservationStatus status = ReservationStatus.IN_PROGRESS;

            ReservationStatus actual = status.handle(CouponEvent.FINISH);

            assertThat(actual).isEqualTo(ReservationStatus.DONE);
        }
    }

    @Nested
    @DisplayName("상태가 CANCELED일 때 ")
    class cancel {

        @Test
        @DisplayName("CANCEL 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_cancel() {
            ReservationStatus status = ReservationStatus.CANCELED;

            assertThatThrownBy(() -> status.handle(CouponEvent.CANCEL))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("DECLINE 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_decline() {
            ReservationStatus status = ReservationStatus.CANCELED;

            assertThatThrownBy(() -> status.handle(CouponEvent.DECLINE))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("ACCEPT 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_accept() {
            ReservationStatus status = ReservationStatus.CANCELED;

            assertThatThrownBy(() -> status.handle(CouponEvent.ACCEPT))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("FINISH 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_finish() {
            ReservationStatus status = ReservationStatus.CANCELED;

            assertThatThrownBy(() -> status.handle(CouponEvent.FINISH))
                .isInstanceOf(InvalidRequestException.class);
        }
    }

    @Nested
    @DisplayName("상태가 DONE일 때 ")
    class done {
        @Test
        @DisplayName("CANCEL 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_cancel() {
            ReservationStatus status = ReservationStatus.DONE;

            assertThatThrownBy(() -> status.handle(CouponEvent.CANCEL))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("DECLINE 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_decline() {
            ReservationStatus status = ReservationStatus.DONE;

            assertThatThrownBy(() -> status.handle(CouponEvent.DECLINE))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("ACCEPT 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_accept() {
            ReservationStatus status = ReservationStatus.DONE;

            assertThatThrownBy(() -> status.handle(CouponEvent.ACCEPT))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("FINISH 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_finish() {
            ReservationStatus status = ReservationStatus.DONE;

            assertThatThrownBy(() -> status.handle(CouponEvent.FINISH))
                .isInstanceOf(InvalidRequestException.class);
        }
    }
}
