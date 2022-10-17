package com.woowacourse.kkogkkog.lazycoupon.domain;

import static com.woowacourse.kkogkkog.lazycoupon.domain.LazyCouponEventType.REGISTER;
import static com.woowacourse.kkogkkog.lazycoupon.domain.LazyCouponEventType.EXPIRE;
import static com.woowacourse.kkogkkog.lazycoupon.domain.LazyCouponStatus.REGISTERED;
import static com.woowacourse.kkogkkog.lazycoupon.domain.LazyCouponStatus.EXPIRED;
import static com.woowacourse.kkogkkog.lazycoupon.domain.LazyCouponStatus.ISSUED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.common.exception.InvalidRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("LazyCouponStatus 클래스의")
class LazyCouponStatusTest {

    @Nested
    @DisplayName("상태가 ISSUED일 때")
    class Issued {

        @Test
        @DisplayName("REGISTER 이벤트를 받으면, REGISTERED 반환한다.")
        void success_register() {
            LazyCouponStatus status = ISSUED;

            LazyCouponStatus actual = status.handle(REGISTER);

            assertThat(actual).isEqualTo(REGISTERED);
        }

        @Test
        @DisplayName("EXPIRE 이벤트를 받으면, EXPIRED를 반환한다.")
        void success_expire() {
            LazyCouponStatus status = ISSUED;

            LazyCouponStatus actual = status.handle(LazyCouponEventType.EXPIRE);

            assertThat(actual).isEqualTo(EXPIRED);
        }
    }

    @Nested
    @DisplayName("상태가 REGISTERED일 때")
    class Registered {

        @Test
        @DisplayName("REGISTER 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_register() {
            LazyCouponStatus status = REGISTERED;

            assertThatThrownBy(() -> status.handle(REGISTER))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("EXPIRE 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_expire() {
            LazyCouponStatus status = REGISTERED;

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
            LazyCouponStatus status = EXPIRED;

            assertThatThrownBy(() -> status.handle(REGISTER))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("EXPIRE 이벤트를 받으면, 예외를 발생시킨다.")
        void fail_expire() {
            LazyCouponStatus status = EXPIRED;

            assertThatThrownBy(() -> status.handle(EXPIRE))
                .isInstanceOf(InvalidRequestException.class);
        }
    }

}
