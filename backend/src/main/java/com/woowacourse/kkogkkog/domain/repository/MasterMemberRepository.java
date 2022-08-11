package com.woowacourse.kkogkkog.domain.repository;

import com.woowacourse.kkogkkog.domain.MasterMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterMemberRepository extends JpaRepository<MasterMember, Long> {
}
