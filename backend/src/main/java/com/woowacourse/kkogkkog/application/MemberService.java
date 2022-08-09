package com.woowacourse.kkogkkog.application;

import static java.util.stream.Collectors.toList;

import com.woowacourse.kkogkkog.application.dto.MemberCreateResponse;
import com.woowacourse.kkogkkog.application.dto.MemberHistoryResponse;
import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.application.dto.MemberUpdateRequest;
import com.woowacourse.kkogkkog.application.dto.MyProfileResponse;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.MemberHistory;
import com.woowacourse.kkogkkog.domain.Workspace;
import com.woowacourse.kkogkkog.domain.repository.MemberHistoryRepository;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.domain.repository.WorkspaceRepository;
import com.woowacourse.kkogkkog.exception.auth.WorkspaceNotFoundException;
import com.woowacourse.kkogkkog.exception.member.MemberHistoryNotFoundException;
import com.woowacourse.kkogkkog.exception.member.MemberNotFoundException;
import com.woowacourse.kkogkkog.infrastructure.SlackUserInfo;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberHistoryRepository memberHistoryRepository;
    private final WorkspaceRepository workspaceRepository;

    public MemberService(MemberRepository memberRepository,
                         MemberHistoryRepository memberHistoryRepository,
                         WorkspaceRepository workspaceRepository) {
        this.memberRepository = memberRepository;
        this.memberHistoryRepository = memberHistoryRepository;
        this.workspaceRepository = workspaceRepository;
    }

    public MemberCreateResponse saveOrFind(SlackUserInfo slackUserInfo) {
        String userId = slackUserInfo.getUserId();
        String workspaceId = slackUserInfo.getTeamId();
        String nickname = slackUserInfo.getName();
        String email = slackUserInfo.getEmail();
        String imageUrl = slackUserInfo.getPicture();

        return memberRepository.findByUserId(userId)
            .stream()
            .map(member -> updateToMatchSlack(member, email, imageUrl))
            .findFirst()
            .orElseGet(() ->
                save(new Member(null, userId, workspaceId, nickname, email, imageUrl)));
    }

    private MemberCreateResponse updateToMatchSlack(Member member, String email, String imageUrl) {
        member.updateEmail(email);
        member.updateImageURL(imageUrl);
        return new MemberCreateResponse(member.getId(), false);
    }

    private MemberCreateResponse save(Member member) {
        memberRepository.save(member);
        return new MemberCreateResponse(member.getId(), true);
    }

    @Transactional(readOnly = true)
    public MyProfileResponse findById(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);
        Workspace workspace = workspaceRepository.findByWorkspaceId(findMember.getWorkspaceId())
            .orElseThrow(WorkspaceNotFoundException::new);
        long unreadHistoryCount = memberHistoryRepository.countByHostMemberAndIsReadFalse(findMember);

        return MyProfileResponse.of(findMember, workspace.getName(), unreadHistoryCount);
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findAll() {
        return memberRepository.findAll().stream()
            .map(MemberResponse::of)
            .collect(toList());
    }

    public void update(MemberUpdateRequest memberUpdateRequest) {
        Member member = memberRepository.findById(memberUpdateRequest.getMemberId())
            .orElseThrow(MemberNotFoundException::new);

        member.updateNickname(memberUpdateRequest.getNickname());
    }

    public List<MemberHistoryResponse> findHistoryById(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);

        return memberHistoryRepository.findAllByHostMemberOrderByCreatedAtDesc(findMember).stream()
            .map(MemberHistoryResponse::of)
            .collect(toList());
    }

    public void updateIsReadMemberHistory(Long memberHistoryId) {
        MemberHistory memberHistory = memberHistoryRepository.findById(memberHistoryId)
            .orElseThrow(MemberHistoryNotFoundException::new);

        memberHistory.updateIsRead();
    }
}
