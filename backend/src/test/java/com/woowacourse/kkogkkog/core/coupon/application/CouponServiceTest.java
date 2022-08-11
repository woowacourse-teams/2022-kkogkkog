package com.woowacourse.kkogkkog.core.coupon.application;

import static com.woowacourse.kkogkkog.common.fixture.domain.CouponFixture.COFFEE;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.AUTHOR;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.JEONG;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.LEO;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.ROOKIE;
import static com.woowacourse.kkogkkog.common.fixture.dto.CouponDtoFixture.COFFEE_쿠폰_저장_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.kkogkkog.common.annotaion.ApplicationTest;
import com.woowacourse.kkogkkog.coupon.application.CouponService;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponReservationResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponSaveRequest;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


@ApplicationTest
@DisplayName("CouponService의")
class CouponServiceTest {

    @Autowired
    private CouponService couponService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CouponRepository couponRepository;

    @Nested
    @DisplayName("save 메서드는")
    class save {

        private Member sender;
        private Member receiver1;
        private Member receiver2;
        private CouponSaveRequest couponSaveRequest;

        @BeforeEach
        void setUp() {
            sender = memberRepository.save(ROOKIE.getMember());
            receiver1 = memberRepository.save(AUTHOR.getMember());
            receiver2 = memberRepository.save(JEONG.getMember());
        }

        @Test
        @DisplayName("생성할 쿠폰 데이터, 보낸 사람과 받는 사람들의 데이터를 가지고 쿠폰들을 생성하고, 생성된 쿠폰들을 반환한다.")
        void success_saveAll() {
            couponSaveRequest = COFFEE_쿠폰_저장_요청(
                sender.getId(), List.of(receiver1.getId(), receiver2.getId()));

            List<CouponResponse> actual = couponService.save(couponSaveRequest);

            assertThat(actual).hasSize(2);
        }
    }

    @Nested
    @DisplayName("findAllBySender 메서드는")
    class findAllBySender {

        private Member sender;
        private Member receiver;

        @BeforeEach
        void setUp() {
            sender = memberRepository.save(JEONG.getMember());
            receiver = memberRepository.save(LEO.getMember());
            couponRepository.save(COFFEE.getCoupon(sender, receiver));
            couponRepository.save(COFFEE.getCoupon(sender, receiver));
        }

        @Test
        @DisplayName("보낸 사람의 ID를 통해, 해당 ID로 보낸 쿠폰 리스트를 반환한다.")
        void success() {
            List<CouponReservationResponse> actual = couponService.findAllBySender(sender.getId());

            List<Long> actualIds = actual.stream()
                .map(it -> it.getMemberId())
                .collect(Collectors.toList());
            assertAll(
                () -> assertThat(actual).hasSize(2),
                () -> assertThat(actualIds).containsOnly(sender.getId())
            );
        }
    }

    @Nested
    @DisplayName("findAllByReceiver 메서드는")
    class findAllByReceiver {

        private Member sender;
        private Member receiver;

        @BeforeEach
        void setUp() {
            sender = memberRepository.save(JEONG.getMember());
            receiver = memberRepository.save(LEO.getMember());
            couponRepository.save(COFFEE.getCoupon(sender, receiver));
            couponRepository.save(COFFEE.getCoupon(sender, receiver));
        }

        @Test
        @DisplayName("받은 사람의 ID를 통해, 해당 ID로 받은 쿠폰 리스트를 반환한다.")
        void success() {
            List<CouponReservationResponse> actual = couponService.findAllByReceiver(receiver.getId());

            List<Long> actualIds = actual.stream()
                .map(it -> it.getMemberId())
                .collect(Collectors.toList());
            assertAll(
                () -> assertThat(actual).hasSize(2),
                () -> assertThat(actualIds).containsOnly(receiver.getId())
            );
        }
    }
}