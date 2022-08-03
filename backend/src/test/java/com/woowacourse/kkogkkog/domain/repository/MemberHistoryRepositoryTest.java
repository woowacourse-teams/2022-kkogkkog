package com.woowacourse.kkogkkog.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.domain.Coupon;
import com.woowacourse.kkogkkog.domain.CouponEvent;
import com.woowacourse.kkogkkog.domain.CouponStatus;
import com.woowacourse.kkogkkog.domain.CouponType;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.MemberHistory;
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
    private MemberHistoryRepository memberHistories;

    @Test
    void countByHostMemberAndIsReadFalse() {
        Member sender = members.save(new Member(null, "UJeong", "T03LX3C5540",
            "정", "jeong@gmail.com", "image"));
        Member receiver = members.save(new Member(null, "ULeo", "T03LX3C5540",
            "레오", "leothelion@gmail.com", "image"));
        Coupon coupon = coupons.save(new Coupon(null, sender, receiver, "한턱쏘는",
            "추가 메세지", "#241223", CouponType.COFFEE, CouponStatus.READY));

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
            coupon.getCouponType(), event, coupon.getMeetingDate());
    }
}
