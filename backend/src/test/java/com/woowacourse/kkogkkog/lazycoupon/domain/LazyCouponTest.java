package com.woowacourse.kkogkkog.lazycoupon.domain;

import static com.woowacourse.kkogkkog.support.fixture.domain.CouponFixture.COFFEE;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.SENDER;
import static com.woowacourse.kkogkkog.lazycoupon.domain.LazyCouponEventType.REGISTER;
import static com.woowacourse.kkogkkog.lazycoupon.domain.LazyCouponStatus.REGISTERED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.coupon.domain.CouponType;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.lazycoupon.exception.LazyCouponQuantityExcessException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class LazyCouponTest {

    @Nested
    @DisplayName("정적 팩토리 메서드는")
    class Constructor {

        @Test
        @DisplayName("UUID4로 생성된 쿠폰코드를 가진 미등록 쿠폰을 생성한다.")
        void success() {
            Member sender = SENDER.getMember();

            LazyCoupon lazyCoupon = LazyCoupon.of(sender, "고마워요!",
                "커피쿠폰입니다.", CouponType.COFFEE);

            assertThat(lazyCoupon.getCouponCode()).isNotEmpty();
        }
    }

    @Nested
    @DisplayName("validateQuantity 정적 메서드는")
    class ValidateQuantity {

        @Test
        @DisplayName("발급 할 수 있는 수량인지 검사한다.")
        void success() {
            assertThatNoException()
                .isThrownBy(() -> LazyCoupon.validateQuantity(5));
        }

        @Test
        @DisplayName("수량이 최댓값 초과이면 예외를 던진다.")
        void fail_over() {
            assertThatThrownBy(() -> LazyCoupon.validateQuantity(6))
                .isInstanceOf(LazyCouponQuantityExcessException.class);
        }

        @Test
        @DisplayName("수량이 최솟값 미만이면 예외를 던진다.")
        void fail_under() {
            assertThatThrownBy(() -> LazyCoupon.validateQuantity(0))
                .isInstanceOf(LazyCouponQuantityExcessException.class);
        }
    }

    @Nested
    @DisplayName("changeStatus 메서드는")
    class ChangeStatus {

        @Test
        @DisplayName("미등록 쿠폰 이벤트를 받으면 상태를 변경한다.")
        void success() {
            Member sender = SENDER.getMember();
            LazyCoupon lazyCoupon = COFFEE.getCouponLazyCoupon(sender).getLazyCoupon();

            lazyCoupon.changeStatus(REGISTER);

            LazyCouponStatus actual = lazyCoupon.getLazyCouponStatus();
            assertThat(actual).isEqualTo(REGISTERED);
        }
    }
    @Nested
    @DisplayName("isNotExpired 메서드는")
    class IsNotExpired {

        @Test
        @DisplayName("만료 시간이 현재 시간보다 나중이면 true를 반환한다.")
        void success_true() {
            Member sender = SENDER.getMember();
            LazyCoupon lazyCoupon = COFFEE.getCouponLazyCoupon(sender).getLazyCoupon();
            ExpirationStrategy strategy = (createdTime) -> {
                LocalDateTime expiredTime = createdTime.plusDays(7);
                return expiredTime.isAfter(LocalDateTime.now());
            };

            boolean actual = lazyCoupon.isNotExpired(strategy);

            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("만료 시간이 현재 시간보다 이전이면 false를 반환한다.")
        void success_false() {
            Member sender = SENDER.getMember();
            LazyCoupon lazyCoupon = COFFEE.getCouponLazyCoupon(sender).getLazyCoupon();
            ExpirationStrategy strategy = (createdTime) -> createdTime.isAfter(LocalDateTime.now());

            boolean actual = lazyCoupon.isNotExpired(strategy);

            assertThat(actual).isFalse();
        }
    }
}
