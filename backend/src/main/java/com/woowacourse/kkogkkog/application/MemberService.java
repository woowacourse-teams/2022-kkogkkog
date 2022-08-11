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
import com.woowacourse.kkogkkog.domain.WorkspaceMember;
import com.woowacourse.kkogkkog.domain.repository.MemberHistoryRepository;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.domain.repository.WorkspaceMemberRepository;
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
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final MemberHistoryRepository memberHistoryRepository;

    public MemberService(MemberRepository memberRepository,
                         WorkspaceMemberRepository workspaceMemberRepository,
                         MemberHistoryRepository memberHistoryRepository) {
        this.memberRepository = memberRepository;
        this.workspaceMemberRepository = workspaceMemberRepository;
        this.memberHistoryRepository = memberHistoryRepository;
    }

    public MemberCreateResponse saveOrUpdate(SlackUserInfo userInfo, Workspace workspace) {
        String userId = userInfo.getUserId();
        String email = userInfo.getEmail();
        String imageUrl = userInfo.getPicture();

        Optional<Member> member = memberRepository.findByUserId(userId);
        if (member.isPresent()) {
            Member existingMember = member.get();
            existingMember.updateEmail(email);
            existingMember.updateImageURL(imageUrl);
            saveOrUpdateWorkspaceMember(existingMember);
            return new MemberCreateResponse(existingMember.getId(), false);
        }
        Member newMember = memberRepository.save(
            Member.ofRandomNickname(userId, workspace, email, imageUrl));
        workspaceMemberRepository.save(WorkspaceMember.of(newMember));
        return new MemberCreateResponse(newMember.getId(), true);
    }

    private void saveOrUpdateWorkspaceMember(Member member) {
        Optional<WorkspaceMember> workspaceMember = workspaceMemberRepository.findByMasterMember(member);
        if (workspaceMember.isEmpty()) {
            workspaceMemberRepository.save(WorkspaceMember.of(member));
            return;
        }
        WorkspaceMember newWorkspaceMember = workspaceMember.get();
        newWorkspaceMember.updateEmail(member.getEmail());
        newWorkspaceMember.updateImageURL(member.getImageUrl());
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
        WorkspaceMember workspaceMember = workspaceMemberRepository.findByMasterMember(member)
            .orElseThrow(MemberNotFoundException::new);

        member.updateNickname(memberUpdateRequest.getNickname());
        workspaceMember.updateNickname(memberUpdateRequest.getNickname());
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
