package com.woowacourse.kkogkkog.coupon.domain.repository;

import static com.woowacourse.kkogkkog.support.fixture.domain.CouponFactory.createCoupon;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.RECEIVER;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.RECEIVER2;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.SENDER;
import static com.woowacourse.kkogkkog.support.fixture.domain.WorkspaceFixture.KKOGKKOG;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.event.CouponEvent;
import com.woowacourse.kkogkkog.coupon.domain.event.CouponEventType;
import com.woowacourse.kkogkkog.coupon.domain.state.CouponState;
import com.woowacourse.kkogkkog.coupon.domain.state.CouponStatus;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import com.woowacourse.kkogkkog.member.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.member.domain.repository.WorkspaceRepository;
import com.woowacourse.kkogkkog.support.repository.RepositoryTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

@RepositoryTest
@DisplayName("CouponRepository의")
class CouponRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

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
            entityManager.flush();
            entityManager.clear();
        }

        // TODO: 정렬 검증도 추가하면 이상적일 듯?
        @DisplayName("보낸 사람의 기준으로 쿠폰 목록을 조회할 수 있다.")
        @Test
        void senderSuccess() {
            couponRepository.save(createCoupon(sender, receiver));
            couponRepository.save(createCoupon(sender, receiver2));
            couponRepository.save(createCoupon(receiver, sender));
            entityManager.flush();
            entityManager.clear();

            Slice<Coupon> actual = couponRepository.findAllBySender(sender, PageRequest.of(0, 5));
            assertThat(actual).hasSize(2);
        }

        @DisplayName("보낸 사람과 상태를 기준으로 쿠폰 목록을 조회할 수 있다.")
        @Test
        void success_senderWithStatus() {
            couponRepository.save(createCoupon(sender, receiver));
            couponRepository.save(createCoupon(sender, receiver));
            Coupon coupon = createCoupon(sender, receiver);
            couponRepository.save(coupon);
            coupon.changeState(
                new CouponEvent(CouponEventType.REQUEST, LocalDateTime.now().plusDays(1L)),
                receiver);
            entityManager.flush();
            entityManager.clear();

            Slice<Coupon> actual = couponRepository.findAllBySender(sender,
                CouponStatus.REQUESTED, PageRequest.of(0, 5));
            assertThat(actual).hasSize(1);
        }

        @DisplayName("받은 사람의 기준으로 쿠폰 목록을 조회할 수 있다.")
        @Test
        void receiverSuccess() {
            couponRepository.save(createCoupon(sender, receiver));
            couponRepository.save(createCoupon(sender, receiver2));
            couponRepository.save(createCoupon(receiver, sender));
            couponRepository.flush();

            Slice<Coupon> actual = couponRepository.findAllByReceiver(receiver, PageRequest.of(0, 5));
            assertThat(actual).hasSize(1);
        }

        @DisplayName("받은 사람과 상태를 기준으로 쿠폰 목록을 조회할 수 있다.")
        @Test
        void success_receiverWithStatus() {
            couponRepository.save(createCoupon(sender, receiver));
            couponRepository.save(createCoupon(sender, receiver));
            Coupon coupon = createCoupon(sender, receiver);
            couponRepository.save(coupon);
            coupon.changeState(new CouponEvent(CouponEventType.REQUEST, LocalDateTime.now().plusDays(1L)), receiver);
            couponRepository.flush();
            entityManager.clear();

            Slice<Coupon> actual = couponRepository.findAllByReceiver(
                receiver, CouponStatus.REQUESTED, PageRequest.of(0, 5));
            assertThat(actual).hasSize(1);
        }
    }

    @Nested
    @DisplayName("findAllByMemberAndCouponStatusOrderByMeetingDate 메서드는")
    class findAllByMemberAndCouponStatusOrderByMeetingDate {

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

        @DisplayName("현재 시간 이후의 같은 상태의 쿠폰들을 조회한다.")
        @Test
        void acceptedCouponsAfterCurrentTime() {
            couponRepository.save(createCoupon(sender, receiver,
                new CouponState(CouponStatus.ACCEPTED, LocalDateTime.now().plusDays(1))));
            couponRepository.save(createCoupon(sender, receiver2,
                new CouponState(CouponStatus.ACCEPTED, LocalDateTime.now())));

            couponRepository.save(createCoupon(sender, receiver,
                new CouponState(CouponStatus.REQUESTED, LocalDateTime.now().plusDays(2))));
            couponRepository.save(createCoupon(sender, receiver,
                new CouponState(CouponStatus.FINISHED, LocalDateTime.now().plusDays(2))));

            LocalDateTime now = LocalDate.now().atStartOfDay();
            List<Coupon> actual = couponRepository.
                findAllByMemberAndCouponStatusOrderByMeetingDate(sender, now, CouponStatus.ACCEPTED);

            assertThat(actual).hasSize(2);
        }

        @DisplayName("현재 시간 이전의 쿠폰들은 조회되지 않는다.")
        @Test
        void couponsBeforeTheCurrentTime() {
            couponRepository.save(createCoupon(sender, receiver,
                new CouponState(CouponStatus.ACCEPTED, LocalDateTime.now().minusDays(1))));
            couponRepository.save(createCoupon(sender, receiver,
                new CouponState(CouponStatus.ACCEPTED, LocalDateTime.now().minusDays(2))));

            LocalDateTime now = LocalDate.now().atStartOfDay();
            List<Coupon> actual = couponRepository.
                findAllByMemberAndCouponStatusOrderByMeetingDate(sender, now, CouponStatus.ACCEPTED);

            assertThat(actual).hasSize(0);
        }
    }
}
