package com.woowacourse.kkogkkog.application;

import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.exception.member.MemberDuplicatedEmail;
import com.woowacourse.kkogkkog.presentation.dto.MemberCreateRequest;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long save(MemberCreateRequest memberCreateRequest) {
        Optional<Member> findMember = memberRepository.findByEmail(memberCreateRequest.getEmail());
        if (findMember.isPresent()) {
            throw new MemberDuplicatedEmail();
        }

        Member member = memberCreateRequest.toEntity();

        return memberRepository.save(member).getId();
    }
}
