package com.woowacourse.kkogkkog.application;

import static java.util.stream.Collectors.toList;

import com.woowacourse.kkogkkog.application.dto.MemberOAuthResponse;
import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.domain.Member2;
import com.woowacourse.kkogkkog.infrastructure.SlackUserInfo;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.repository.MemberOAuthRepository;
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
    private final MemberOAuthRepository memberOAuthRepository;

    public MemberService(MemberRepository memberRepository,
        MemberOAuthRepository memberOAuthRepository) {
        this.memberRepository = memberRepository;
        this.memberOAuthRepository = memberOAuthRepository;
    }

    public Long save(MemberCreateRequest memberCreateRequest) {
        Optional<Member> findMember = memberRepository.findByEmail(memberCreateRequest.getEmail());
        if (findMember.isPresent()) {
            throw new MemberDuplicatedEmail();
        }

        Member member = memberCreateRequest.toEntity();

        return memberRepository.save(member).getId();
    }

    public MemberOAuthResponse saveOrFind(SlackUserInfo slackUserInfo) {
        String socialId = slackUserInfo.getUserId();
        String nickname = slackUserInfo.getName();
        String imageUri = slackUserInfo.getImageUri();
        String teamId = slackUserInfo.getTeamId();

        return memberOAuthRepository.findBySocialId(socialId)
            .stream()
            .map(it -> {
                it.updateImage(imageUri);
                return new MemberOAuthResponse(it.getId(), false);
            })
            .findFirst()
            .orElseGet(() ->
                {
                    Member2 savedMember = new Member2(null, nickname, imageUri, socialId, teamId);
                    memberOAuthRepository.save(savedMember);
                    return new MemberOAuthResponse(savedMember.getId(), true);
                }
            );
    }

//    public MemberOAuthResponse saveOrFind(SlackUserInfo response) {
//        String socialId = response.getUserId();
//        String nickname = response.getName();
//        String imageUri = response.getImageUri();
//        String teamId = response.getTeamId();
//
//        Optional<Member2> member = memberOAuthRepository.findBySocialId(socialId);
//        if (member.isPresent()) {
//            member.get().updateImage(imageUri);
//            return new MemberOAuthResponse(member.get().getId(), false);
//        }
//        Member2 savedMember = new Member2(null, nickname, imageUri, socialId, teamId);
//        memberOAuthRepository.save(savedMember);
//
//        return new MemberOAuthResponse(savedMember.getId(), true);
//    }

    @Transactional(readOnly = true)
    public MemberResponse findById(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);

        return MemberResponse.of(findMember);
    }

    public List<MemberResponse> findAll() {
        return memberRepository.findAll().stream()
            .map(MemberResponse::of)
            .collect(toList());
    }
}
