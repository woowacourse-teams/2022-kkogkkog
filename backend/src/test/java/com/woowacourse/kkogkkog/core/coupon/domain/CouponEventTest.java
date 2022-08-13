package com.woowacourse.kkogkkog.core.coupon.domain;

import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.JEONG;
import static com.woowacourse.kkogkkog.coupon.domain.CouponEvent.CANCEL;
import static com.woowacourse.kkogkkog.coupon.domain.CouponEvent.DECLINE;
import static com.woowacourse.kkogkkog.coupon.domain.CouponEvent.FINISH;
import static com.woowacourse.kkogkkog.coupon.domain.CouponEvent.REQUEST;
import static com.woowacourse.kkogkkog.fixture.WorkspaceFixture.KKOGKKOG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.coupon.domain.CouponType;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.exception.ForbiddenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("CouponEvent 클래스의")
class CouponEventTest {

    @Nested
    @DisplayName("REQUEST 요청은")
    class request {

        @Test
        @DisplayName("받은 사람이 요청할 경우, 성공적으로 요청을 보낼 수 있다.")
        void success() {
            boolean isSender = false;
            boolean isReceiver = true;

            assertThatNoException()
                .isThrownBy(() -> REQUEST.checkExecutable(isSender, isReceiver));
        }

        @Test
        @DisplayName("보낸 사람이 요청할 경우, 예외를 발생시킨다.")
        void fail_senderRequest() {
            boolean isSender = true;
            boolean isReceiver = false;

            assertThatThrownBy(() -> REQUEST.checkExecutable(isSender, isReceiver))
                .isInstanceOf(ForbiddenException.class);
        }
    }

    @Nested
    @DisplayName("CANCEL 요청은")
    class cancel {

        @Test
        @DisplayName("받은 사람이 요청을 할 경우, 성공적으로 요청을 보낼 수 있다.")
        void success() {
            boolean isSender = false;
            boolean isReceiver = true;

            assertDoesNotThrow(() -> CANCEL.checkExecutable(isSender, isReceiver));
        }

        @Test
        @DisplayName("보낸 사람이 요청을 할 경우, 예외를 발생시킨다.")
        void fail_senderCancel() {
            boolean isSender = true;
            boolean isReceiver = false;

            assertThatThrownBy(() -> CANCEL.checkExecutable(isSender, isReceiver))
                .isInstanceOf(ForbiddenException.class);
        }
    }

    @Nested
    @DisplayName("DECLINE 요청은")
    class decline {

        @Test
        @DisplayName("보낸 사람이 요청을 할 경우, 성공적으로 요청을 보낼 수 있다.")
        void success() {
            boolean isSender = true;
            boolean isReceiver = true;

            assertDoesNotThrow(() -> DECLINE.checkExecutable(isSender, isReceiver));
        }

        @Test
        @DisplayName("받은 사람이 보내면, 예외를 발생시킨다.")
        void fail_canNotAccept() {
            boolean isSender = false;
            boolean isReceiver = true;

            assertThatThrownBy(() -> DECLINE.checkExecutable(isSender, isReceiver))
                .isInstanceOf(ForbiddenException.class);
        }
    }

    @Nested
    @DisplayName("FINISH 요청은")
    class finish {

        @Test
        @DisplayName("보낸 사람이 요청을 할 경우, 성공적으로 요청을 보낼 수 있다.")
        void success_sender() {
            boolean isSender = true;
            boolean isReceiver = false;

            assertDoesNotThrow(() -> FINISH.checkExecutable(isSender, isReceiver));
        }

        @Test
        @DisplayName("받은 사람이 요청을 할 경우, 성공적으로 요청을 보낼 수 있다.")
        void success_receiver() {
            boolean isSender = false;
            boolean isReceiver = true;

            assertDoesNotThrow(() -> FINISH.checkExecutable(isSender, isReceiver));
        }

        @Test
        @DisplayName("보낸 사람, 받은 사람이 아닐 경우, 예외를 발생시킨다.")
        void fail_otherMember() {
            boolean isSender = false;
            boolean isReceiver = false;

            assertThatThrownBy(() -> FINISH.checkExecutable(isSender, isReceiver))
                .isInstanceOf(ForbiddenException.class);
        }
    }

    @Nested
    @DisplayName("generateNoticeMessage 메서드는")
    class GenerateNoticeMessage {

        @Test
        @DisplayName("회원과 쿠폰 종류를 받아 알림 메시지를 반환한다.")
        void formatString() {
            Member member = JEONG.getMember(KKOGKKOG.getWorkspace());
            CouponEvent couponEvent = CouponEvent.INIT;
            CouponType meal = CouponType.MEAL;

            String actual = couponEvent.generateNoticeMessage(member, meal);
            String expected = "정님이 식사 쿠폰을 보냈어요.";

            assertThat(actual).isEqualTo(expected);
        }
    }
}
