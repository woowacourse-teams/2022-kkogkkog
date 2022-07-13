package com.woowacourse.kkogkkog.application;

import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.presentation.dto.MemberCreateRequest;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long save(MemberCreateRequest memberCreateRequest) {
        Member member = memberCreateRequest.toEntity();

        return memberRepository.save(member).getId();
    }
}
