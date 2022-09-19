package com.woowacourse.kkogkkog.coupon.domain;

import static com.woowacourse.kkogkkog.coupon.domain.CouponEventType.CANCEL;
import static com.woowacourse.kkogkkog.coupon.domain.CouponEventType.DECLINE;
import static com.woowacourse.kkogkkog.coupon.domain.CouponEventType.FINISH;
import static com.woowacourse.kkogkkog.coupon.domain.CouponEventType.REQUEST;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.JEONG;
import static com.woowacourse.kkogkkog.support.fixture.domain.WorkspaceFixture.KKOGKKOG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.kkogkkog.common.exception.ForbiddenException;
import com.woowacourse.kkogkkog.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("CouponEventType 클래스의")
class CouponEventTypeTest {

    @Nested
    @DisplayName("REQUEST 요청은")
    class Request {

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

        @Test
        @DisplayName("예약날짜 정보를 필요로 한다.")
        void needsMeetingDate() {
            assertThat(REQUEST.needsMeetingDate()).isTrue();
        }
    }

    @Nested
    @DisplayName("CANCEL 요청은")
    class Cancel {

        @Test
        @DisplayName("받은 사람이 요청을 할 경우, 성공적으로 요청을 보낼 수 있다.")
        void success() {
            boolean isSender = false;
            boolean isReceiver = true;

            assertDoesNotThrow(() -> CANCEL.checkExecutable(isSender, isReceiver));
        }

        @Test
        @DisplayName("보낸 사람이 요청을 할 경우, 성공적으로 요청을 보낼 수 있다.")
        void fail_senderCancel() {
            boolean isSender = true;
            boolean isReceiver = false;

            assertDoesNotThrow(() -> CANCEL.checkExecutable(isSender, isReceiver));
        }
    }

    @Nested
    @DisplayName("DECLINE 요청은")
    class Decline {

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
    class Finish {

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
            CouponEventType couponEvent = CouponEventType.INIT;
            CouponType meal = CouponType.MEAL;

            String actual = couponEvent.generateNoticeMessage(member, meal);
            String expected = "`진우` 님이 `식사` 쿠폰을 *보냈어요*👋";

            assertThat(actual).isEqualTo(expected);
        }
    }
}
