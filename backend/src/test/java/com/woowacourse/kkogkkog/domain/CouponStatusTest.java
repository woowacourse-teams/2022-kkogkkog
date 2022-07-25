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

    @Test
    @DisplayName("REQUESTED 상태의 쿠폰은 CANCEL 이벤트를 받으면 READY 상태로 변경된다.")
    void requestedChangesToReadyOnCancelEvent() {
        CouponStatus currentStatus = CouponStatus.REQUESTED;
        CouponEvent event = CouponEvent.CANCEL;

        CouponStatus actual = currentStatus.handle(event);
        CouponStatus expected = CouponStatus.READY;

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("READY 상태의 쿠폰은 CANCEL 이벤트를 받으면 예외가 발생된다.")
    void readyCanNotHandleCancelEvent() {
        CouponStatus currentStatus = CouponStatus.READY;
        CouponEvent event = CouponEvent.CANCEL;

        assertThatThrownBy(() -> currentStatus.handle(event))
                .isInstanceOf(InvalidRequestException.class);
    }

    @Test
    @DisplayName("REQUESTED 상태의 쿠폰은 DECLINE 이벤트를 받으면 READY 상태로 변경된다.")
    void requestedChangesToReadyOnDeclineEvent() {
        CouponStatus currentStatus = CouponStatus.REQUESTED;
        CouponEvent event = CouponEvent.DECLINE;

        CouponStatus actual = currentStatus.handle(event);
        CouponStatus expected = CouponStatus.READY;

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("READY 상태의 쿠폰은 DECLINE 이벤트를 받으면 예외가 발생된다.")
    void readyCanNotHandleDeclineEvent() {
        CouponStatus currentStatus = CouponStatus.READY;
        CouponEvent event = CouponEvent.DECLINE;

        assertThatThrownBy(() -> currentStatus.handle(event))
            .isInstanceOf(InvalidRequestException.class);
    }

    @Test
    @DisplayName("REQUESTED 상태의 쿠폰은 ACCEPT 이벤트를 받으면 ACCEPTED 상태로 변경된다.")
    void requestedChangesToAcceptedOnAcceptEvent() {
        CouponStatus currentStatus = CouponStatus.REQUESTED;
        CouponEvent event = CouponEvent.ACCEPT;

        CouponStatus actual = currentStatus.handle(event);
        CouponStatus expected = CouponStatus.ACCEPTED;

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("READY 상태의 쿠폰은 ACCEPT 이벤트를 받으면 예외가 발생된다.")
    void readyCanNotHandleAcceptEvent() {
        CouponStatus currentStatus = CouponStatus.READY;
        CouponEvent event = CouponEvent.ACCEPT;

        assertThatThrownBy(() -> currentStatus.handle(event))
                .isInstanceOf(InvalidRequestException.class);
    }

    @Test
    @DisplayName("READY 상태의 쿠폰은 FINISH 이벤트를 받으면 FINISHED 상태로 변경된다.")
    void readyChangesToFinishOnFinishedEvent() {
        CouponStatus currentStatus = CouponStatus.READY;
        CouponEvent event = CouponEvent.FINISH;

        CouponStatus actual = currentStatus.handle(event);
        CouponStatus expected = CouponStatus.FINISHED;

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("REQUESTED 상태의 쿠폰은 FINISH 이벤트를 받으면 FINISHED 상태로 변경된다.")
    void requestedChangesToFinishOnFinishedEvent() {
        CouponStatus currentStatus = CouponStatus.REQUESTED;
        CouponEvent event = CouponEvent.FINISH;

        CouponStatus actual = currentStatus.handle(event);
        CouponStatus expected = CouponStatus.FINISHED;

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("ACCEPTED 상태의 쿠폰은 FINISH 이벤트를 받으면 FINISHED 상태로 변경된다.")
    void acceptedChangesToFinishOnFinishedEvent() {
        CouponStatus currentStatus = CouponStatus.ACCEPTED;
        CouponEvent event = CouponEvent.FINISH;

        CouponStatus actual = currentStatus.handle(event);
        CouponStatus expected = CouponStatus.FINISHED;

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("FINISHED 상태의 쿠폰은 FINISH 이벤트를 받으면 예외가 발생된다.")
    void finishedCanNotHandleFinishEvent() {
        CouponStatus currentStatus = CouponStatus.FINISHED;
        CouponEvent event = CouponEvent.FINISH;

        assertThatThrownBy(() -> currentStatus.handle(event))
            .isInstanceOf(InvalidRequestException.class);
    }
}
