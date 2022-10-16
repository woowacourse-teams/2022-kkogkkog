package com.woowacourse.kkogkkog.unregisteredcoupon.domain;

import static com.woowacourse.kkogkkog.support.fixture.domain.CouponFixture.COFFEE;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.RECEIVER;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.SENDER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.coupon.domain.CouponType;
import com.woowacourse.kkogkkog.unregisteredcoupon.exception.UnregisteredCouponQuantityExcessException;
import com.woowacourse.kkogkkog.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class UnregisteredCouponTest {

    @Nested
    @DisplayName("정적 팩토리 메서드는")
    class Constructor {

        @Test
        @DisplayName("UUID4로 생성된 쿠폰코드를 가진 미등록 쿠폰을 생성한다.")
        void success() {
            Member sender = SENDER.getMember();

            UnregisteredCoupon unregisteredCoupon = UnregisteredCoupon.of(sender, "고마워요!",
                "커피쿠폰입니다.", CouponType.COFFEE);

            assertThat(unregisteredCoupon.getCouponCode()).isNotEmpty();
        }
    }

    @Nested
    @DisplayName("validateQuantity 정적 메서드는")
    class ValidateQuantity {

        @Test
        @DisplayName("발급 할 수 있는 수량인지 검사한다.")
        void success() {
            assertThatNoException()
                .isThrownBy(() -> UnregisteredCoupon.validateQuantity(5));
        }

        @Test
        @DisplayName("수량이 최댓값 초과이면 예외를 던진다.")
        void fail_over() {
            assertThatThrownBy(() -> UnregisteredCoupon.validateQuantity(6))
                .isInstanceOf(UnregisteredCouponQuantityExcessException.class);
        }

        @Test
        @DisplayName("수량이 최솟값 미만이면 예외를 던진다.")
        void fail_under() {
            assertThatThrownBy(() -> UnregisteredCoupon.validateQuantity(0))
                .isInstanceOf(UnregisteredCouponQuantityExcessException.class);
        }
    }

    @Nested
    @DisplayName("registerCoupon 매서드는")
    class RegisterCoupon {

        @Test
        @DisplayName("받는 사람을 받으면 등록 처리 후 쿠폰을 연결한다.")
        void success() {
            Member sender = SENDER.getMember();
            Member receiver = RECEIVER.getMember();
            UnregisteredCoupon unregisteredCoupon = COFFEE.getUnregisteredCoupon(sender);

            unregisteredCoupon.registerCoupon(receiver);

            UnregisteredCouponStatus actual = unregisteredCoupon.getUnregisteredCouponStatus();
            assertThat(actual).isEqualTo(UnregisteredCouponStatus.REGISTERED);
        }
    }
}
