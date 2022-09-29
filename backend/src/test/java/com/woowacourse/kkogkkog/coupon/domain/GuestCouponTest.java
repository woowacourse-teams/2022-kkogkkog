package com.woowacourse.kkogkkog.coupon.domain;

import static com.woowacourse.kkogkkog.support.fixture.domain.CouponFixture.COFFEE;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.SENDER;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class GuestCouponTest {

    @Nested
    @DisplayName("정적 팩토리 메서드는")
    class Constructor {

        @Test
        @DisplayName("UUID4로 생성된 쿠폰코드를 가진 무기명 쿠폰을 생성한다.")
        void success() {
            Member sender = SENDER.getMember();

            GuestCoupon guestCoupon = COFFEE.getGuestCoupon(sender);

            assertThat(guestCoupon.getCouponCode()).isNotNull();
        }
    }
}
