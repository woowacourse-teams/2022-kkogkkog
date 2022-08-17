package com.woowacourse.kkogkkog.member.domain.repository;

import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.MemberHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberHistoryRepository extends JpaRepository<MemberHistory, Long> {

    List<MemberHistory> findAllByHostMemberOrderByCreatedTimeDesc(Member member);

    long countByHostMemberAndIsReadFalse(Member member);

    List<MemberHistory> findAllByCouponIdOrderByCreatedTimeDesc(Long couponId);
}
