package com.woowacourse.kkogkkog.application;

import static java.util.stream.Collectors.toList;

import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.exception.member.MemberNotFoundException;
import com.woowacourse.kkogkkog.application.dto.MemberCreateResponse;
import com.woowacourse.kkogkkog.infrastructure.SlackUserInfo;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberCreateResponse saveOrFind(SlackUserInfo slackUserInfo) {
        String userId = slackUserInfo.getUserId();
        String workspaceId = slackUserInfo.getTeamId();
        String nickname = slackUserInfo.getName();
        String imageUrl = slackUserInfo.getPicture();

        return memberRepository.findByUserId(userId)
            .stream()
            .map(member -> update(member, imageUrl))
            .findFirst()
            .orElseGet(() ->
                save(new Member(null, userId, workspaceId, nickname, imageUrl)));
    }

    private MemberCreateResponse update(Member member, String imageUrl) {
        member.updateImageURL(imageUrl);
        return new MemberCreateResponse(member.getId(), false);
    }

    private MemberCreateResponse save(Member member) {
        memberRepository.save(member);
        return new MemberCreateResponse(member.getId(), true);
    }

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
