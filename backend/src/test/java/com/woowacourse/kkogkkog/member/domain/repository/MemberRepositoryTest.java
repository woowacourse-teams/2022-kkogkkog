package com.woowacourse.kkogkkog.member.domain.repository;

import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.LEO;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.ROOKIE;
import static com.woowacourse.kkogkkog.support.fixture.domain.WorkspaceFixture.KKOGKKOG;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import com.woowacourse.kkogkkog.support.repository.RepositoryTest;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class MemberRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원의 nickname이 앞글자부터 동일한 nickname을 찾는다.")
    void findByNickname() {
        Workspace workspace = workspaceRepository.save(KKOGKKOG.getWorkspace());
        memberRepository.save(ROOKIE.getMember(workspace));
        memberRepository.save(LEO.getMember(workspace));
        entityManager.clear();

        List<Member> members = memberRepository.findByNickname("루");

        assertThat(members).hasSize(1);
    }
}
