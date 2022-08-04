package com.woowacourse.kkogkkog.domain;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Nested
    @DisplayName("DECLINE 이벤트는")
    class Decline {

        @Test
        @DisplayName("보낸 사람이 보낼 수 있다.")
        void success() {
            boolean isSender = true;
            boolean isReceiver = false;

            assertThatNoException()
                .isThrownBy(() -> CouponEvent.DECLINE.checkExecutable(isSender, isReceiver));
        }

        @Test
        @DisplayName("받은 사람이 보내면, 예외를 던진다.")
        void fail_receiverDecline() {
            boolean isSender = false;
            boolean isReceiver = true;

            assertThatThrownBy(() -> CouponEvent.DECLINE.checkExecutable(isSender, isReceiver))
                .isInstanceOf(ForbiddenException.class);
        }
    }

    @Nested
    @DisplayName("ACCEPT 이벤트는")
    class Accept {

        @Test
        @DisplayName("보낸 사람이 보낼 수 있다.")
        void senderCanAccept() {
            boolean isSender = true;
            boolean isReceiver = false;

            assertThatNoException()
                .isThrownBy(() -> CouponEvent.ACCEPT.checkExecutable(isSender, isReceiver));
        }

        @Test
        @DisplayName("받은 사람이 보내면, 예외를 던진다.")
        void receiverCanNotAccept() {
            boolean isSender = false;
            boolean isReceiver = true;

            assertThatThrownBy(() -> CouponEvent.ACCEPT.checkExecutable(isSender, isReceiver))
                .isInstanceOf(ForbiddenException.class);
        }
    }

    @Nested
    @DisplayName("FINISH 이벤트는")
    class Finish {

        @Test
        @DisplayName("보낸 사람이 보낼 수 있다.")
        void senderCanFinish() {
            boolean isSender = true;
            boolean isReceiver = false;

            assertThatNoException()
                .isThrownBy(() -> CouponEvent.FINISH.checkExecutable(isSender, isReceiver));
        }

        @Test
        @DisplayName("받은 사람이 보낼 수 있다.")
        void receiverCanFinish() {
            boolean isSender = false;
            boolean isReceiver = true;

            assertThatNoException()
                .isThrownBy(() -> CouponEvent.FINISH.checkExecutable(isSender, isReceiver));
        }

        @Test
        @DisplayName("쿠폰과 관계없는 사람이 보내면, 예외를 던진다.")
        void otherCanNotFinish() {
            boolean isSender = false;
            boolean isReceiver = false;

            assertThatThrownBy(() -> CouponEvent.FINISH.checkExecutable(isSender, isReceiver))
                .isInstanceOf(ForbiddenException.class);
        }
    }

    @Nested
    @DisplayName("generateNoticeMessage 메서드는")
    class GenerateNoticeMessage {

        @Test
        @DisplayName("회원과 쿠폰 종류를 받아 알림 메시지를 반환한다.")
        void formatString() {
            Member member = new Member(null, "UJeong", "T03LX3C5540",
                "정", "jeong@gmail.com", "image");
            CouponEvent couponEvent = CouponEvent.INIT;
            CouponType meal = CouponType.MEAL;

            String actual = couponEvent.generateNoticeMessage(member, meal);
            String expected = "정님이 식사 쿠폰을 보냈어요.";

            assertThat(actual).isEqualTo(expected);
        }
    }
}
