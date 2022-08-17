package com.woowacourse.kkogkkog.core.coupon.domain.query;

import static com.woowacourse.kkogkkog.support.fixture.domain.CouponFixture.COFFEE;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.RECEIVER;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.SENDER;
import static com.woowacourse.kkogkkog.support.fixture.domain.ReservationFixture.RESERVE_SAVE;
import static com.woowacourse.kkogkkog.support.fixture.domain.WorkspaceFixture.KKOGKKOG;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.support.setup.RepositoryTest;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.coupon.domain.query.CouponQueryRepository;
import com.woowacourse.kkogkkog.coupon.domain.query.CouponReservationData;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import com.woowacourse.kkogkkog.member.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.member.domain.repository.WorkspaceRepository;
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
    private WorkspaceRepository workspaceRepository;
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
        private Coupon coupon;

        @BeforeEach
        void setUp() {
            Workspace workspace = workspaceRepository.save(KKOGKKOG.getWorkspace());
            sender = memberRepository.save(SENDER.getMember(workspace));
            receiver = memberRepository.save(RECEIVER.getMember(workspace));
            coupon = couponRepository.save(COFFEE.getCoupon(sender, receiver));
            couponRepository.save(COFFEE.getCoupon(sender, receiver));
            couponRepository.save(COFFEE.getCoupon(sender, receiver));
        }

        @DisplayName("보낸 사람의 기준으로 쿠폰 목록을 조회할 수 있다.")
        @Test
        void findAllBySender() {
            // given
            requestAndCancelReservation(coupon);
            requestAndCancelReservation(coupon);
            requestAndCancelReservation(coupon);
            couponRepository.flush();
            reservationRepository.flush();

            // when
            reservationRepository.save(RESERVE_SAVE.getReservation(coupon, LocalDateTime.now()));

            // then
            List<CouponReservationData> actual = couponQueryRepository.findAllBySender(sender);
            assertThat(actual).hasSize(3);
        }

        private void requestAndCancelReservation(Coupon coupon) {
            Reservation reservation = reservationRepository.save(
                RESERVE_SAVE.getReservation(coupon, LocalDateTime.now()));
            coupon.changeStatus(CouponEvent.REQUEST, receiver);
            reservation.changeCouponStatus(CouponEvent.CANCEL, receiver);
            reservationRepository.delete(reservation);
            couponRepository.flush();
            reservationRepository.flush();

        }
    }
}
