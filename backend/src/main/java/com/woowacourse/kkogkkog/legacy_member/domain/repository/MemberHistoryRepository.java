package com.woowacourse.kkogkkog.legacy_member.domain.repository;

import com.woowacourse.kkogkkog.legacy_member.domain.LegacyMemberHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberHistoryRepository extends JpaRepository<LegacyMemberHistory, Long> {

    List<LegacyMemberHistory> findAllByCouponIdOrderByCreatedTimeDesc(Long couponId);
}
