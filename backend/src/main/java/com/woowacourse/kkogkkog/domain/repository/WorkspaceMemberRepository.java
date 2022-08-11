package com.woowacourse.kkogkkog.domain.repository;

import com.woowacourse.kkogkkog.domain.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, Long> {
}
