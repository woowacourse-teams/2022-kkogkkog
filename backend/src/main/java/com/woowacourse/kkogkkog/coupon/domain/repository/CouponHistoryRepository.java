package com.woowacourse.kkogkkog.coupon.domain.repository;

import com.woowacourse.kkogkkog.coupon.domain.CouponHistory;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponHistoryRepository extends JpaRepository<CouponHistory, Long> {

    default CouponHistory findCouponHistory(Long id) {
        return findById(id).orElseThrow(MemberHistoryNotFoundException::new);
    }

    List<CouponHistory> findAllByHostMemberOrderByCreatedTimeDesc(Member member);

    long countByHostMemberAndIsReadFalse(Member member);

    List<CouponHistory> findAllByCouponIdOrderByCreatedTimeDesc(Long couponId);
}
