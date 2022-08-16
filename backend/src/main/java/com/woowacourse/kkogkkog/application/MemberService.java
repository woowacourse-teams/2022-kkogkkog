package com.woowacourse.kkogkkog.application;

import static java.util.stream.Collectors.toList;

import com.woowacourse.kkogkkog.application.dto.MemberCreateResponse;
import com.woowacourse.kkogkkog.application.dto.MemberHistoryResponse;
import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.application.dto.MemberUpdateRequest;
import com.woowacourse.kkogkkog.application.dto.MyProfileResponse;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.MemberHistory;
import com.woowacourse.kkogkkog.domain.Nickname;
import com.woowacourse.kkogkkog.domain.Workspace;
import com.woowacourse.kkogkkog.domain.WorkspaceUser;
import com.woowacourse.kkogkkog.domain.repository.MemberHistoryRepository;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.domain.repository.WorkspaceUserRepository;
import com.woowacourse.kkogkkog.exception.member.MemberHistoryNotFoundException;
import com.woowacourse.kkogkkog.exception.member.MemberNotFoundException;
import com.woowacourse.kkogkkog.infrastructure.SlackUserInfo;
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
            return updateExistingUser(userInfo, workspaceUser.get());
        }
        Optional<Member> member = memberRepository.findByEmail(userInfo.getEmail());
        if (member.isPresent()) {
            return integrateNewWorkspaceUser(userInfo, member.get(), workspace);
        }
        return saveNewUser(userInfo, workspace);
    }

    private MemberCreateResponse updateExistingUser(SlackUserInfo userInfo,
                                                    WorkspaceUser workspaceUser) {
        workspaceUser.updateDisplayName(userInfo.getName());
        workspaceUser.updateImageURL(userInfo.getPicture());
        Member member = workspaceUser.getMasterMember();
        member.updateImageURL(userInfo.getPicture());
        return new MemberCreateResponse(member.getId(), false);
    }

    private MemberCreateResponse integrateNewWorkspaceUser(SlackUserInfo userInfo,
                                                           Member existingMember,
                                                           Workspace workspace) {
        existingMember.updateImageURL(userInfo.getPicture());
        workspaceUserRepository.save(
            new WorkspaceUser(null, existingMember, userInfo.getUserId(), workspace,
                userInfo.getName(), userInfo.getEmail(), userInfo.getPicture()));
        return new MemberCreateResponse(existingMember.getId(), false);
    }

    private MemberCreateResponse saveNewUser(SlackUserInfo userInfo, Workspace workspace) {
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
}
