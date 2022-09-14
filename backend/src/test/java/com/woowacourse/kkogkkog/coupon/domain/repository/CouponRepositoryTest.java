package com.woowacourse.kkogkkog.coupon.domain.repository;

import static com.woowacourse.kkogkkog.support.fixture.domain.CouponFixture.ACCEPTED_COUPON;
import static com.woowacourse.kkogkkog.support.fixture.domain.CouponFixture.COFFEE;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.RECEIVER;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.RECEIVER2;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.SENDER;
import static com.woowacourse.kkogkkog.support.fixture.domain.WorkspaceFixture.KKOGKKOG;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponState;
import com.woowacourse.kkogkkog.coupon.domain.CouponStatus;
import com.woowacourse.kkogkkog.coupon.domain.CouponType;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import com.woowacourse.kkogkkog.member.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.member.domain.repository.WorkspaceRepository;
import com.woowacourse.kkogkkog.support.repository.RepositoryTest;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class CouponRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private WorkspaceRepository workspaceRepository;
    @Autowired
    private CouponRepository couponRepository;

    @Nested
    @DisplayName("보유하고 있는 쿠폰 중 ")
    class FindAllBy {

        private Member sender;
        private Member receiver;
        private Member receiver2;

        @BeforeEach
        void setUp() {
            Workspace workspace = workspaceRepository.save(KKOGKKOG.getWorkspace());
            sender = memberRepository.save(SENDER.getMember(workspace));
            receiver = memberRepository.save(RECEIVER.getMember(workspace));
            receiver2 = memberRepository.save(RECEIVER2.getMember(workspace));
        }

        // TODO: 정렬 검증도 추가하면 이상적일 듯?
        @DisplayName("보낸 사람의 기준으로 쿠폰 목록을 조회할 수 있다.")
        @Test
        void senderSuccess() {
            couponRepository.save(COFFEE.getCoupon(sender, receiver));
            couponRepository.save(COFFEE.getCoupon(sender, receiver2));
            couponRepository.save(COFFEE.getCoupon(receiver, sender));
            couponRepository.flush();

            List<Coupon> actual = couponRepository.findAllBySender(sender);
            assertThat(actual).hasSize(2);
        }

        @DisplayName("받은 사람의 기준으로 쿠폰 목록을 조회할 수 있다.")
        @Test
        void receiverSuccess() {
            couponRepository.save(COFFEE.getCoupon(sender, receiver));
            couponRepository.save(COFFEE.getCoupon(sender, receiver2));
            couponRepository.save(COFFEE.getCoupon(receiver, sender));
            couponRepository.flush();

            List<Coupon> actual = couponRepository.findAllByReceiver(receiver);
            assertThat(actual).hasSize(1);
        }
    }

    @Nested
    @DisplayName("미팅이 확정된 쿠폰 중 ")
    class findAllByMemberAndMeetingDate {

        private Member sender;
        private Member receiver;
        private Member receiver2;
        private Coupon coupon1;
        private Coupon coupon2;

        @BeforeEach
        void setUp() {
            Workspace workspace = workspaceRepository.save(KKOGKKOG.getWorkspace());
            sender = memberRepository.save(SENDER.getMember(workspace));
            receiver = memberRepository.save(RECEIVER.getMember(workspace));
            receiver2 = memberRepository.save(RECEIVER2.getMember(workspace));
        }

        @DisplayName("현재 시간 이후의 쿠폰들을 조회한다.")
        @Test
        void couponsAfterTheCurrentTime() {
            // given
            coupon1 = couponRepository.save(ACCEPTED_COUPON.getCoupon(
                sender, receiver, CouponType.COFFEE,
                new CouponState(CouponStatus.ACCEPTED, LocalDateTime.now().plusDays(1))));
            coupon2 = couponRepository.save(ACCEPTED_COUPON.getCoupon(
                sender, receiver, CouponType.COFFEE,
                new CouponState(CouponStatus.ACCEPTED, LocalDateTime.now().plusDays(1))));
            coupon2 = couponRepository.save(ACCEPTED_COUPON.getCoupon(
                sender, receiver, CouponType.COFFEE,
                new CouponState(CouponStatus.ACCEPTED, LocalDateTime.now().plusDays(8))));

            LocalDateTime nowDate = LocalDateTime.now();

            // when
            List<Coupon> extract = couponRepository.findAllByMemberAndMeetingDate(sender, nowDate);

            // then
            assertThat(extract).hasSize(3);
        }

        @DisplayName("현재 시간 이전의 쿠폰들은 조회되지 않는다.")
        @Test
        void couponsBeforeTheCurrentTime() {
            // given
            coupon1 = couponRepository.save(ACCEPTED_COUPON.getCoupon(
                sender, receiver, CouponType.COFFEE,
                new CouponState(CouponStatus.ACCEPTED, LocalDateTime.now().minusDays(1))));
            coupon2 = couponRepository.save(ACCEPTED_COUPON.getCoupon(
                sender, receiver, CouponType.COFFEE,
                new CouponState(CouponStatus.ACCEPTED, LocalDateTime.now().minusDays(2))));

            LocalDateTime nowDate = LocalDateTime.now();

            // when
            List<Coupon> extract = couponRepository.findAllByMemberAndMeetingDate(sender, nowDate);

            // then
            assertThat(extract).hasSize(0);
        }
    }
}
