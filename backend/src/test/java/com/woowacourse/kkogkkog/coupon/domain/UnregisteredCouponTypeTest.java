package com.woowacourse.kkogkkog.coupon.domain;

import static com.woowacourse.kkogkkog.coupon.domain.UnregisteredCouponEventType.CONSUME;
import static com.woowacourse.kkogkkog.coupon.domain.UnregisteredCouponEventType.EXPIRE;
import static com.woowacourse.kkogkkog.coupon.domain.UnregisteredCouponStatus.CONSUMED;
import static com.woowacourse.kkogkkog.coupon.domain.UnregisteredCouponStatus.EXPIRED;
import static com.woowacourse.kkogkkog.coupon.domain.UnregisteredCouponStatus.ISSUED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.common.exception.InvalidRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("UnregisteredCouponStatus 클래스의")
class UnregisteredCouponStatusTest {

    @Nested
    @DisplayName("상태가 ISSUED일 때")
    class ISSUED {

        @Test
        @DisplayName("CONSUME 이벤트를 받으면, CONSUMED를 반환한다.")
        void success_consume() {
            UnregisteredCouponStatus status = ISSUED;

            UnregisteredCouponStatus actual = status.handle(CONSUME);

            assertThat(actual).isEqualTo(CONSUMED);
        }

        @Test
        @DisplayName("EXPIRE 이벤트를 받으면, EXPIRED를 반환한다.")
        void success_expire() {
            UnregisteredCouponStatus status = ISSUED;

            UnregisteredCouponStatus actual = status.handle(UnregisteredCouponEventType.EXPIRE);

            assertThat(actual).isEqualTo(EXPIRED);
        }
    }

    @Nested
    @DisplayName("상태가 CONSUMED일 때")
    class CONSUMED {

        @Test
        @DisplayName("CONSUME 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_consume() {
            UnregisteredCouponStatus status = CONSUMED;

            assertThatThrownBy(() -> status.handle(CONSUME))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("EXPIRE 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_expire() {
            UnregisteredCouponStatus status = CONSUMED;

            assertThatThrownBy(() -> status.handle(EXPIRE))
                .isInstanceOf(InvalidRequestException.class);
        }
    }

    @Nested
    @DisplayName("상태가 EXPIRED일 때")
    class EXPIRED {

        @Test
        @DisplayName("CONSUME 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_consume() {
            UnregisteredCouponStatus status = EXPIRED;

            assertThatThrownBy(() -> status.handle(CONSUME))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("EXPIRE 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_expire() {
            UnregisteredCouponStatus status = EXPIRED;

            assertThatThrownBy(() -> status.handle(EXPIRE))
                .isInstanceOf(InvalidRequestException.class);
        }
    }

}
