package com.woowacourse.kkogkkog.core.coupon.domain;

import static com.woowacourse.kkogkkog.common.fixture.domain.CouponFixture.COFFEE;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.RECEIVER;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.ROOKIE;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.SENDER;
import static com.woowacourse.kkogkkog.coupon.domain.CouponEvent.FINISH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.exception.SameSenderReceiverException;
import com.woowacourse.kkogkkog.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Coupon 클래스의")
class CouponTest {

    @Nested
    @DisplayName("쿠폰을 생성할 때")
    class constructor {

        @Test
        @DisplayName("보낸 사람과 받는 사람이 동일하면, 예외를 발생시킨다.")
        void fail_sameSenderAndReceiver() {
            Member member = ROOKIE.getMember();

            assertThatThrownBy(() -> COFFEE.getCoupon(member, member))
                .isInstanceOf(SameSenderReceiverException.class);
        }
    }

    @Nested
    @DisplayName("changeStatus 매서드는")
    class changeStatus {

        @Test
        @DisplayName("CouponEvent와 회원을 통해서 CouponStatus를 변경한다.")
        void success() {
            Member sender = SENDER.getMember();
            Member receiver = RECEIVER.getMember();

            Coupon coffee = COFFEE.getCoupon(sender, receiver);

            assertDoesNotThrow(() -> coffee.changeStatus(FINISH, sender));
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

            Coupon coffee = COFFEE.getCoupon(sender, receiver);

            Member actual = coffee.getOppositeMember(sender);
            assertThat(actual).isEqualTo(receiver);
        }
    }
}
