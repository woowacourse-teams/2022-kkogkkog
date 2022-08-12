package com.woowacourse.kkogkkog.domain.repository;

import static com.woowacourse.kkogkkog.common.fixture.domain.CouponFixture.COFFEE;
import static com.woowacourse.kkogkkog.fixture.WorkspaceFixture.KKOGKKOG;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.MemberHistory;
import com.woowacourse.kkogkkog.domain.Workspace;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberHistoryRepositoryTest {

    @Autowired
    private CouponRepository coupons;

    @Autowired
    private MemberRepository members;

    @Autowired
    private WorkspaceRepository workspaces;
    @Autowired
    private MemberHistoryRepository memberHistories;

    @Test
    @DisplayName("countByHostMemberAndIsReadFalse 메서드는 기록의 주인이 읽지 안은 기록들의 개수를 반환한다.")
    void countByHostMemberAndIsReadFalse() {
        Workspace workspace = workspaces.save(KKOGKKOG.getWorkspace());
        Member sender = members.save(new Member(null, "UJeong", workspace,
            "정", "jeong@gmail.com", "image"));
        Member receiver = members.save(new Member(null, "ULeo", workspace,
            "레오", "leothelion@gmail.com", "image"));
        Coupon coupon = COFFEE.getCoupon(sender, receiver);

        MemberHistory initHistory = toMemberHistory(receiver, sender, coupon, CouponEvent.INIT);
        initHistory.updateIsRead();
        memberHistories.save(initHistory);
        memberHistories.save(toMemberHistory(sender, receiver, coupon, CouponEvent.REQUEST));
        memberHistories.save(toMemberHistory(receiver, sender, coupon, CouponEvent.DECLINE));
        memberHistories.flush();

        long actual = memberHistories.countByHostMemberAndIsReadFalse(receiver);
        assertThat(actual).isEqualTo(1L);
    }

    private MemberHistory toMemberHistory(Member hostMember, Member targetMember, Coupon coupon,
                                          CouponEvent event) {
        return new MemberHistory(null, hostMember, targetMember, coupon.getId(),
            coupon.getCouponType(), event, null);
    }
}
