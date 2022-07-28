package com.woowacourse.kkogkkog.domain.repository;

import com.woowacourse.kkogkkog.domain.Member2;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberOAuthRepository extends JpaRepository<Member2, Long> {

    Optional<Member2> findBySocialId(String slackId);
}
