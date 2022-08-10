package com.woowacourse.kkogkkog.core.reservation.application;

import static com.woowacourse.kkogkkog.common.fixture.domain.CouponFixture.COFFEE;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.AUTHOR;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.ROOKIE;
import static com.woowacourse.kkogkkog.common.fixture.dto.ReservationDtoFixture.예약_저장_요청;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.common.annotaion.ApplicationTest;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.reservation.application.ReservationService;
import com.woowacourse.kkogkkog.reservation.application.dto.ReservationSaveRequest;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationTest
@DisplayName("ReservationService의")
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CouponRepository couponRepository;

    @Nested
    @DisplayName("save 메서드는")
    class save {
        private Member sender;
        private Member receiver;
        private Coupon coupon;
        private ReservationSaveRequest reservationSaveRequest;

        @BeforeEach
        void setUp() {
            sender = memberRepository.save(ROOKIE.getMember());
            receiver = memberRepository.save(AUTHOR.getMember());
            coupon = couponRepository.save(COFFEE.getCoupon(sender, receiver));
        }

        @Test
        @DisplayName("쿠폰을 통해서 예약을 생성하고, 예약 ID를 반환한다.")
        void success() {
            reservationSaveRequest = 예약_저장_요청(coupon.getId(), LocalDate.now());

            Long actual = reservationService.save(reservationSaveRequest);

            assertThat(actual).isNotNull();
        }
    }
}
