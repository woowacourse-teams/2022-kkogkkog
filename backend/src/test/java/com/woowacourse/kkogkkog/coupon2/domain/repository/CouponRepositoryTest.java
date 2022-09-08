package com.woowacourse.kkogkkog.coupon2.domain.repository;

import static com.woowacourse.kkogkkog.support.fixture.domain.CouponFixture2.COFFEE;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.RECEIVER;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.RECEIVER2;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.SENDER;
import static com.woowacourse.kkogkkog.support.fixture.domain.WorkspaceFixture.KKOGKKOG;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.coupon2.domain.Coupon;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import com.woowacourse.kkogkkog.member.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.member.domain.repository.WorkspaceRepository;
import com.woowacourse.kkogkkog.support.repository.RepositoryTest;
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
    @DisplayName("보유하고 있는 쿠폰 중, ")
    class CouponQuery {

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
        void findAllBySender() {
            couponRepository.save(COFFEE.getCoupon(sender, receiver));
            couponRepository.save(COFFEE.getCoupon(sender, receiver2));
            couponRepository.save(COFFEE.getCoupon(receiver, sender));
            couponRepository.flush();

            List<Coupon> actual = couponRepository.findAllBySender(sender);
            assertThat(actual).hasSize(2);
        }
    }
}
