package com.woowacourse.kkogkkog.unregisteredcoupon.domain;

import static com.woowacourse.kkogkkog.unregisteredcoupon.UnregisteredCouponEventType.REGISTER;
import static com.woowacourse.kkogkkog.unregisteredcoupon.UnregisteredCouponEventType.EXPIRE;
import static com.woowacourse.kkogkkog.unregisteredcoupon.UnregisteredCouponStatus.REGISTERED;
import static com.woowacourse.kkogkkog.unregisteredcoupon.UnregisteredCouponStatus.EXPIRED;
import static com.woowacourse.kkogkkog.unregisteredcoupon.UnregisteredCouponStatus.ISSUED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.common.exception.InvalidRequestException;
import com.woowacourse.kkogkkog.unregisteredcoupon.UnregisteredCouponEventType;
import com.woowacourse.kkogkkog.unregisteredcoupon.UnregisteredCouponStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("UnregisteredCouponStatus 클래스의")
class UnregisteredCouponStatusTest {

    @Nested
    @DisplayName("상태가 ISSUED일 때")
    class Issued {

        @Test
        @DisplayName("REGISTER 이벤트를 받으면, REGISTERED 반환한다.")
        void success_register() {
            UnregisteredCouponStatus status = ISSUED;

            UnregisteredCouponStatus actual = status.handle(REGISTER);

            assertThat(actual).isEqualTo(REGISTERED);
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
    @DisplayName("상태가 REGISTERED일 때")
    class Registered {

        @Test
        @DisplayName("REGISTER 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_register() {
            UnregisteredCouponStatus status = REGISTERED;

            assertThatThrownBy(() -> status.handle(REGISTER))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("EXPIRE 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_expire() {
            UnregisteredCouponStatus status = REGISTERED;

            assertThatThrownBy(() -> status.handle(EXPIRE))
                .isInstanceOf(InvalidRequestException.class);
        }
    }

    @Nested
    @DisplayName("상태가 REGISTERED일 때")
    class Expired {

        @Test
        @DisplayName("REGISTER 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_register() {
            UnregisteredCouponStatus status = EXPIRED;

            assertThatThrownBy(() -> status.handle(REGISTER))
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
