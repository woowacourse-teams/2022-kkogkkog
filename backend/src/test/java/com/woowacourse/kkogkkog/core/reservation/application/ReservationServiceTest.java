package com.woowacourse.kkogkkog.core.reservation.application;

import static com.woowacourse.kkogkkog.common.fixture.domain.CouponFixture.COFFEE;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.AUTHOR;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.ROOKIE;
import static com.woowacourse.kkogkkog.common.fixture.domain.ReservationFixture.RESERVE_SAVE;
import static com.woowacourse.kkogkkog.common.fixture.dto.ReservationDtoFixture.예약_수정_요청;
import static com.woowacourse.kkogkkog.common.fixture.dto.ReservationDtoFixture.예약_저장_요청;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.kkogkkog.common.annotaion.ApplicationTest;
import com.woowacourse.kkogkkog.coupon.application.CouponService;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponDetailResponse;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponStatus;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.reservation.application.ReservationService;
import com.woowacourse.kkogkkog.reservation.application.dto.ReservationSaveRequest;
import com.woowacourse.kkogkkog.reservation.application.dto.ReservationUpdateRequest;
import com.woowacourse.kkogkkog.reservation.domain.Reservation;
import com.woowacourse.kkogkkog.reservation.domain.repository.ReservationRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private CouponService couponService;

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
            reservationSaveRequest = 예약_저장_요청(receiver.getId(), coupon.getId(), LocalDateTime.now());

            Long actual = reservationService.save(reservationSaveRequest);

            assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(couponRepository.findById(coupon.getId()).get().getCouponStatus())
                    .isEqualTo(CouponStatus.REQUESTED)
            );
        }
    }

    @Nested
    @DisplayName("update 메서드는")
    class update {

        private Member sender;
        private Member receiver;
        private Coupon coupon;
        private Reservation reservation;
        private ReservationUpdateRequest reservationUpdateRequest;

        @BeforeEach
        void setUp() {
            sender = memberRepository.save(ROOKIE.getMember());
            receiver = memberRepository.save(AUTHOR.getMember());
            coupon = couponRepository.save(COFFEE.getCoupon(sender, receiver, "REQUESTED"));
            reservation = reservationRepository.save(RESERVE_SAVE.getReservation(coupon, now()));
        }

        @Test
        @DisplayName("예약 ID, 요청회원 ID, 쿠폰 Event를 통해서, 예약 상태를 변경한다.")
        void success() {
            reservationUpdateRequest = 예약_수정_요청(sender.getId(), reservation.getId(), "ACCEPT");

            assertAll(
                () -> assertDoesNotThrow(() -> reservationService.update(reservationUpdateRequest)),
                () -> assertThat(
                    couponRepository.findById(1L).get().getCouponStatus()).isNotEqualTo(
                    CouponStatus.REQUESTED)
            );
        }
    }

    @Nested
    @DisplayName("find 메서드는")
    class find {

        private Member sender;
        private Member receiver;
        private Coupon coupon;
//        private Reservation reservation;
        private ReservationSaveRequest reservationSaveRequest;
        private ReservationUpdateRequest reservationUpdateRequest;

        @BeforeEach
        void setUp() {
            sender = memberRepository.save(ROOKIE.getMember());
            receiver = memberRepository.save(AUTHOR.getMember());
            coupon = couponRepository.save(COFFEE.getCoupon(sender, receiver));
//            reservation = reservationRepository.save(RESERVE_SAVE.getReservation(coupon, now()));
        }

        @Test
        @DisplayName("쿠폰 아이디를 받으면, 상세정보를 반환한다.")
        void success() {
            reservationSaveRequest = 예약_저장_요청(receiver.getId(), coupon.getId(), LocalDateTime.now());
            Long save = reservationService.save(reservationSaveRequest);

            reservationUpdateRequest = 예약_수정_요청(sender.getId(), save, "ACCEPT");
            reservationService.update(reservationUpdateRequest);

            CouponDetailResponse couponDetailResponse = couponService.find(coupon.getId());
            System.out.println(couponDetailResponse);
        }
    }
}
