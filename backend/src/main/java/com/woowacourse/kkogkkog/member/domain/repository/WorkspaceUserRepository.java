package com.woowacourse.kkogkkog.member.domain.repository;

import com.woowacourse.kkogkkog.member.domain.WorkspaceUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser, Long> {

    Optional<WorkspaceUser> findByUserId(String userId);
}
