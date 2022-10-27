package com.woowacourse.kkogkkog.coupon.domain;

import static com.woowacourse.kkogkkog.coupon.domain.event.CouponEventType.FINISH;
import static com.woowacourse.kkogkkog.support.fixture.domain.CouponFactory.createCoupon;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.RECEIVER;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.RECEIVER2;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.ROOKIE;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.SENDER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.kkogkkog.coupon.domain.event.CouponEvent;
import com.woowacourse.kkogkkog.coupon.exception.SameSenderReceiverException;
import com.woowacourse.kkogkkog.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Coupon 클래스의")
class CouponTest {

    @Nested
    @DisplayName("생성자에서 쿠폰을 생성할 때")
    class Constructor {

        @Test
        @DisplayName("보낸 사람과 받는 사람이 동일하면, 예외를 발생시킨다.")
        void fail_sameSenderAndReceiver() {
            Member member = ROOKIE.getMember();

            assertThatThrownBy(() -> createCoupon(member, member))
                .isInstanceOf(SameSenderReceiverException.class);
        }
    }

    @Nested
    @DisplayName("changeState 매서드는")
    class ChangeState {

        @Test
        @DisplayName("CouponEvent와 회원을 통해서 CouponStatus를 변경한다.")
        void success() {
            Member sender = SENDER.getMember();
            Member receiver = RECEIVER.getMember();

            Coupon coffee = createCoupon(sender, receiver);

            assertDoesNotThrow(() -> coffee.changeState(new CouponEvent(FINISH, null), sender));
        }
    }

    @Nested
    @DisplayName("getOppositeMember 메서드는")
    class GetOppositeMember {

        @Test
        @DisplayName("Coupon의 회원을 통해서, 상대 회원을 반환한다.")
        void success() {
            Member sender = SENDER.getMember();
            Member receiver = RECEIVER.getMember();

            Coupon coffee = createCoupon(sender, receiver);

            Member actual = coffee.getOppositeMember(sender);
            assertThat(actual).isEqualTo(receiver);
        }
    }

    @Nested
    @DisplayName("isSenderOrReceiver 메서드는")
    class IsSenderOrReceiver {

        @Test
        @DisplayName("보낸 사람 또는 받는 사람이면, true 를 반환한다.")
        void success_true() {
            Member sender = SENDER.getMember();
            Member receiver = RECEIVER.getMember();
            Coupon coupon = createCoupon(sender, receiver);

            Boolean actual = coupon.isSenderOrReceiver(receiver);

            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("보낸 사람이 아니고 받는 사람도 아니면, false 를 반환한다.")
        void success_false() {
            Member sender = SENDER.getMember();
            Member receiver = RECEIVER.getMember();
            Coupon coupon = createCoupon(sender, receiver);

            Member receiver2 = RECEIVER2.getMember();
            Boolean actual = coupon.isSenderOrReceiver(receiver2);

            assertThat(actual).isFalse();
        }
    }
}
