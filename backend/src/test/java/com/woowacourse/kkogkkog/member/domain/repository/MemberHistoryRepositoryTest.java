package com.woowacourse.kkogkkog.member.domain.repository;

import static com.woowacourse.kkogkkog.support.fixture.domain.CouponFixture.createCoupon;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.JEONG;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.LEO;
import static com.woowacourse.kkogkkog.support.fixture.domain.WorkspaceFixture.KKOGKKOG;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.coupon.domain.CouponEventType;
import com.woowacourse.kkogkkog.coupon.domain.CouponHistory;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponHistoryRepository;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import com.woowacourse.kkogkkog.support.repository.RepositoryTest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class MemberHistoryRepositoryTest {

    @Autowired
    private MemberRepository members;

    @Autowired
    private WorkspaceRepository workspaces;

    @Autowired
    private CouponRepository coupons;

    @Autowired
    private CouponHistoryRepository couponHistories;

    @Test
    @DisplayName("countByHostMemberAndIsReadFalse 메서드는 기록의 주인이 읽지 않은 기록들의 개수를 반환한다.")
    void countByHostMemberAndIsReadFalse() {
        Workspace workspace = workspaces.save(KKOGKKOG.getWorkspace());
        Member sender = members.save(JEONG.getMember(workspace));
        Member receiver = members.save(LEO.getMember(workspace));
        Coupon coupon = coupons.save(createCoupon(sender, receiver));

        CouponHistory initHistory = CouponHistory.ofNew(coupon);
        initHistory.updateIsRead();
        couponHistories.save(initHistory);
        couponHistories.save(new CouponHistory(sender, receiver, coupon, new CouponEvent(
            CouponEventType.REQUEST, LocalDateTime.now()), "메시지"));
        couponHistories.save(new CouponHistory(receiver, sender, coupon, new CouponEvent(
            CouponEventType.DECLINE, null), "메시지"));
        couponHistories.flush();

        long actual = couponHistories.countByHostMemberAndIsReadFalse(receiver);
        assertThat(actual).isEqualTo(1L);
    }
}
