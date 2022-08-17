package com.woowacourse.kkogkkog.member.domain.repository;

import com.woowacourse.kkogkkog.member.domain.Workspace;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

    Optional<Workspace> findByWorkspaceId(String workspaceId);
}
