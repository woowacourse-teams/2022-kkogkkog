package com.woowacourse.kkogkkog.domain.repository;

import com.woowacourse.kkogkkog.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
