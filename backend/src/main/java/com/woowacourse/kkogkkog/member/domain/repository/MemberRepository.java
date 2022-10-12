package com.woowacourse.kkogkkog.member.domain.repository;

import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.exception.MemberNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    default Member get(Long id) {
        return findById(id).orElseThrow(MemberNotFoundException::new);
    }

    Optional<Member> findByEmail(String email);

    @Query("SELECT m FROM Member m WHERE m.nickname.value LIKE :nickname%")
    List<Member> findByNickname(@Param("nickname") String nickname);
}
