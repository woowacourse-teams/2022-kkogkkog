package com.woowacourse.kkogkkog.application;

import static java.util.stream.Collectors.*;

import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.application.dto.MembersResponse;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.exception.member.MemberDuplicatedEmail;
import com.woowacourse.kkogkkog.exception.member.MemberNotFoundException;
import com.woowacourse.kkogkkog.presentation.dto.MemberCreateRequest;
import java.util.List;
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

    @Transactional(readOnly = true)
    public MemberResponse findById(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);

        return MemberResponse.of(findMember);

    public MembersResponse findAll() {
        List<MemberResponse> memberResponses = memberRepository.findAll().stream()
            .map(it -> MemberResponse.of(it))
            .collect(toList());

        return new MembersResponse(memberResponses);
    }
}
