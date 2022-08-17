package com.woowacourse.kkogkkog.member.application;

import static java.util.stream.Collectors.toList;

import com.woowacourse.kkogkkog.auth.application.dto.MemberCreateResponse;
import com.woowacourse.kkogkkog.member.application.dto.MemberHistoryResponse;
import com.woowacourse.kkogkkog.member.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.member.application.dto.MemberUpdateRequest;
import com.woowacourse.kkogkkog.member.application.dto.MyProfileResponse;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.MemberHistory;
import com.woowacourse.kkogkkog.member.domain.Nickname;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import com.woowacourse.kkogkkog.member.domain.WorkspaceUser;
import com.woowacourse.kkogkkog.member.domain.repository.MemberHistoryRepository;
import com.woowacourse.kkogkkog.member.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.member.domain.repository.WorkspaceUserRepository;
import com.woowacourse.kkogkkog.member.exception.MemberHistoryNotFoundException;
import com.woowacourse.kkogkkog.member.exception.MemberNotFoundException;
import com.woowacourse.kkogkkog.infrastructure.dto.SlackUserInfo;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final WorkspaceUserRepository workspaceUserRepository;
    private final MemberHistoryRepository memberHistoryRepository;

    public MemberService(MemberRepository memberRepository,
                         WorkspaceUserRepository workspaceUserRepository,
                         MemberHistoryRepository memberHistoryRepository) {
        this.memberRepository = memberRepository;
        this.workspaceUserRepository = workspaceUserRepository;
        this.memberHistoryRepository = memberHistoryRepository;
    }

    public MemberCreateResponse saveOrUpdate(SlackUserInfo userInfo, Workspace workspace) {
        String userId = userInfo.getUserId();
        Optional<WorkspaceUser> workspaceUser = workspaceUserRepository.findByUserId(userId);
        if (workspaceUser.isPresent()) {
            return updateExistingMember(userInfo, workspaceUser.get());
        }
        Optional<Member> member = memberRepository.findByEmail(userInfo.getEmail());
        if (member.isPresent()) {
            return integrateNewWorkspaceUser(userInfo, member.get(), workspace);
        }
        return saveNewMember(userInfo, workspace);
    }

    private MemberCreateResponse updateExistingMember(SlackUserInfo userInfo,
                                                      WorkspaceUser workspaceUser) {
        workspaceUser.updateDisplayName(userInfo.getName());
        workspaceUser.updateImageURL(userInfo.getPicture());

        Member member = workspaceUser.getMasterMember();
        member.updateMainSlackUserId(userInfo.getUserId());
        member.updateImageURL(userInfo.getPicture());
        return new MemberCreateResponse(member.getId(), false);
    }

    private MemberCreateResponse integrateNewWorkspaceUser(SlackUserInfo userInfo,
                                                           Member existingMember,
                                                           Workspace workspace) {
        existingMember.updateImageURL(userInfo.getPicture());
        existingMember.updateMainSlackUserId(userInfo.getUserId());
        workspaceUserRepository.save(
            new WorkspaceUser(null, existingMember, userInfo.getUserId(), workspace,
                userInfo.getName(), userInfo.getEmail(), userInfo.getPicture()));
        return new MemberCreateResponse(existingMember.getId(), false);
    }

    private MemberCreateResponse saveNewMember(SlackUserInfo userInfo, Workspace workspace) {
        String userId = userInfo.getUserId();
        String nickname = userInfo.getName();
        String email = userInfo.getEmail();
        String imageUrl = userInfo.getPicture();

        Member newMember = memberRepository.save(
            new Member(null, userId, workspace, Nickname.ofRandom(), email, imageUrl));
        workspaceUserRepository.save(
            new WorkspaceUser(null, newMember, userId, workspace, nickname, email, imageUrl));
        return new MemberCreateResponse(newMember.getId(), true);
    }

    @Transactional(readOnly = true)
    public MyProfileResponse findById(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);
        long unreadHistoryCount = memberHistoryRepository.countByHostMemberAndIsReadFalse(
            findMember);

        return MyProfileResponse.of(findMember, unreadHistoryCount);
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

        return memberHistoryRepository.findAllByHostMemberOrderByCreatedTimeDesc(findMember)
            .stream()
            .map(MemberHistoryResponse::of)
            .collect(toList());
    }

    public void updateIsReadMemberHistory(Long memberHistoryId) {
        MemberHistory memberHistory = memberHistoryRepository.findById(memberHistoryId)
            .orElseThrow(MemberHistoryNotFoundException::new);

        memberHistory.updateIsRead();
    }

    public void updateAllIsReadMemberHistories(Long memberId) {
        Member foundMember = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);

        List<MemberHistory> memberHistories = memberHistoryRepository.findAllByHostMemberOrderByCreatedTimeDesc(
            foundMember);
        for (MemberHistory memberHistory : memberHistories) {
            memberHistory.updateIsRead();
        }
    }
}