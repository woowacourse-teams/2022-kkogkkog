package com.woowacourse.kkogkkog.core.coupon.domain.query;

import static com.woowacourse.kkogkkog.common.fixture.domain.CouponFixture.COFFEE;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.RECEIVER;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.SENDER;
import static com.woowacourse.kkogkkog.common.fixture.domain.ReservationFixture.RESERVE_SAVE;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.common.annotaion.RepositoryTest;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.coupon.domain.query.CouponQueryRepository;
import com.woowacourse.kkogkkog.coupon.domain.query.CouponReservationData;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.reservation.domain.Reservation;
import com.woowacourse.kkogkkog.reservation.domain.repository.ReservationRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class CouponQueryRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private CouponQueryRepository couponQueryRepository;

    @Nested
    @DisplayName("보유하고 있는 쿠폰 중, ")
    class couponQuery {

        private Member sender;
        private Member receiver;
        private Coupon coupon1;

        private Reservation reservation;

        @BeforeEach
        void setUp() {
            sender = memberRepository.save(SENDER.getMember());
            receiver = memberRepository.save(RECEIVER.getMember());
            coupon1 = couponRepository.save(COFFEE.getCoupon(sender, receiver));
            couponRepository.save(COFFEE.getCoupon(sender, receiver));
            reservation = reservationRepository.save(RESERVE_SAVE.getReservation(coupon1, LocalDateTime.now()));
        }

        @DisplayName("받 사람의 기준으로 쿠폰 목록을 조회할 수 있다.")
        @Test
        void findAllBySender() {
            // given
            reservation.changeCouponStatus(CouponEvent.REQUEST, receiver);
            reservation.changeStatus(CouponEvent.CANCEL);
            couponRepository.flush();
            reservationRepository.flush();
            // when
            reservationRepository.save(RESERVE_SAVE.getReservation(coupon1, LocalDateTime.now()));

            // then
            List<CouponReservationData> actual = couponQueryRepository.findAllBySender(sender);
            assertThat(actual).hasSize(2);
        }
    }
}
