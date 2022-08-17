package com.woowacourse.kkogkkog.coupon.application;

import static com.woowacourse.kkogkkog.support.fixture.domain.CouponFixture.COFFEE;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.JEONG;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.LEO;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.RECEIVER;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.RECEIVER2;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.SENDER;
import static com.woowacourse.kkogkkog.support.fixture.dto.CouponDtoFixture.COFFEE_쿠폰_저장_요청;
import static com.woowacourse.kkogkkog.support.fixture.domain.WorkspaceFixture.KKOGKKOG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.kkogkkog.support.setup.ApplicationTest;
import com.woowacourse.kkogkkog.support.fixture.dto.CouponDtoFixture;
import com.woowacourse.kkogkkog.coupon.application.CouponService;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponDetailResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponHistoryResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponReservationResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponSaveRequest;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.MemberHistory;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import com.woowacourse.kkogkkog.member.domain.repository.MemberHistoryRepository;
import com.woowacourse.kkogkkog.member.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.member.domain.repository.WorkspaceRepository;
import com.woowacourse.kkogkkog.reservation.application.dto.ReservationSaveRequest;
import java.time.LocalDateTime;
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
    @Autowired
    private MemberHistoryRepository memberHistoryRepository;
    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Nested
    @DisplayName("save 메서드는")
    class save {

        private Member sender;
        private Member receiver1;
        private Member receiver2;
        private Workspace workspace;
        private CouponSaveRequest couponSaveRequest;

        // TODO: 이부분에서 Message 로직이 강하게 엮어 있어 Fixture 사용 불가. slack Message는 분리가 필요
        @BeforeEach
        void setUp() {
            workspace = workspaceRepository.save(KKOGKKOG.getWorkspace());
            sender = memberRepository.save(SENDER.getMember(workspace));
            receiver1 = memberRepository.save(RECEIVER.getMember(workspace));
            receiver2 = memberRepository.save(RECEIVER2.getMember(workspace));
        }

        @Test
        @DisplayName("생성할 쿠폰 데이터, 보낸 사람과 받는 사람들의 데이터를 가지고 쿠폰들을 생성하고, 생성된 쿠폰들을 반환한다.")
        void success_saveAll() {
            couponSaveRequest = COFFEE_쿠폰_저장_요청(
                sender.getId(), List.of(receiver1.getId(), receiver2.getId()));

            List<CouponResponse> actual = couponService.save(couponSaveRequest);

            assertThat(actual).hasSize(2);
        }

        @Test
        @DisplayName("쿠폰을 생성할 때, 쿠폰 사용 내역을 기록한다.")
        void success_couponSave() {
            List<CouponResponse> response = couponService.save(
                CouponDtoFixture.COFFEE_쿠폰_저장_요청(sender.getId(), List.of(receiver1.getId())));

            Long couponId = response.get(0).getId();
            List<MemberHistory> memberHistories = memberHistoryRepository.findAllByCouponIdOrderByCreatedTimeDesc(
                couponId);
            assertThat(memberHistories).hasSize(1);
        }
    }

    @Nested
    @DisplayName("findAllBySender 메서드는")
    class findAllBySender {

        private Member sender;
        private Member receiver;

        @BeforeEach
        void setUp() {
            Workspace workspace = workspaceRepository.save(KKOGKKOG.getWorkspace());
            sender = memberRepository.save(JEONG.getMember(workspace));
            receiver = memberRepository.save(LEO.getMember(workspace));
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
                () -> assertThat(actualIds).containsOnly(receiver.getId())
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
            Workspace workspace = workspaceRepository.save(KKOGKKOG.getWorkspace());
            sender = memberRepository.save(JEONG.getMember(workspace));
            receiver = memberRepository.save(LEO.getMember(workspace));
            couponRepository.save(COFFEE.getCoupon(sender, receiver));
            couponRepository.save(COFFEE.getCoupon(sender, receiver));
        }

        @Test
        @DisplayName("받은 사람의 ID를 통해, 해당 ID로 받은 쿠폰 리스트를 반환한다.")
        void success() {
            List<CouponReservationResponse> actual = couponService.findAllByReceiver(
                receiver.getId());

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
    @DisplayName("find 메서드는")
    class Find {

        private Workspace workspace;
        private Member sender;
        private Member receiver;
        private ReservationSaveRequest reservationSaveRequest;

        @BeforeEach
        void setUp() {
            Workspace workspace = workspaceRepository.save(KKOGKKOG.getWorkspace());
            sender = memberRepository.save(JEONG.getMember(workspace));
            receiver = memberRepository.save(LEO.getMember(workspace));
        }

        @Test
        @DisplayName("쿠폰 아이디를 받으면, 쿠폰 상세 정보를 반환한다.")
        void success() {
            List<CouponResponse> response = couponService.save(
                CouponDtoFixture.COFFEE_쿠폰_저장_요청(sender.getId(), List.of(receiver.getId())));
            Long couponId = response.get(0).getId();

            CouponDetailResponse couponDetailResponse = couponService.find(couponId);
            String couponStatus = couponDetailResponse.getCouponStatus();
            LocalDateTime meetingDate = couponDetailResponse.getMeetingDate();
            List<CouponHistoryResponse> couponHistories = couponDetailResponse.getCouponHistories();

            assertAll(
                () -> assertThat(couponStatus).isEqualTo("READY"),
                () -> assertThat(meetingDate).isNull(),
                () -> assertThat(couponHistories).hasSize(1)
            );
        }
    }
}
