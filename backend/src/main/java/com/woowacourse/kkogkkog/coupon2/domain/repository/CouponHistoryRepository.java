package com.woowacourse.kkogkkog.coupon2.domain.repository;

import com.woowacourse.kkogkkog.coupon2.domain.CouponHistory;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponHistoryRepository extends JpaRepository<CouponHistory, Long> {

    List<CouponHistory> findAllByHostMember(Member member);

    List<CouponHistory> findAllByHostMemberOrderByCreatedTimeDesc(Member member);

    long countByHostMemberAndIsReadFalse(Member member);

    List<CouponHistory> findAllByCouponIdOrderByCreatedTimeDesc(Long couponId);
}
