package com.woowacourse.kkogkkog.domain.repository;

import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.MemberHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberHistoryRepository extends JpaRepository<MemberHistory, Long> {

    List<MemberHistory> findAllByHostMemberOrderByCreatedAtDesc(Member member);

    long countByHostMemberAndIsReadFalse(Member member);
}
