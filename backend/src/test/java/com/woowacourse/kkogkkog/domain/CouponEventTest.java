package com.woowacourse.kkogkkog.domain;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.exception.ForbiddenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("CouponEvent 클래스의")
class CouponEventTest {

    @Nested
    @DisplayName("REQUEST 이벤트는")
    class Request {

        @Test
        @DisplayName("받은 사람이 보낼 수 있다.")
        void success() {
            boolean isSender = false;
            boolean isReceiver = true;

            assertThatNoException()
                    .isThrownBy(() -> CouponEvent.REQUEST.checkExecutable(isSender, isReceiver));
        }

        @Test
        @DisplayName("보낸 사람이 보내면, 예외를 던진다.")
        void fail_senderRequest() {
            boolean isSender = true;
            boolean isReceiver = false;

            assertThatThrownBy(() -> CouponEvent.REQUEST.checkExecutable(isSender, isReceiver))
                    .isInstanceOf(ForbiddenException.class);
        }
    }

    @Nested
    @DisplayName("CANCEL 이벤트는")
    class Cancel {

        @Test
        @DisplayName("받은 사람이 보낼 수 있다.")
        void success() {
            boolean isSender = false;
            boolean isReceiver = true;

            assertThatNoException()
                    .isThrownBy(() -> CouponEvent.CANCEL.checkExecutable(isSender, isReceiver));
        }

        @Test
        @DisplayName("보낸 사람이 보내면, 예외를 던진다.")
        void fail_senderCancel() {
            boolean isSender = true;
            boolean isReceiver = false;

            assertThatThrownBy(() -> CouponEvent.CANCEL.checkExecutable(isSender, isReceiver))
                    .isInstanceOf(ForbiddenException.class);
        }
    }
}
