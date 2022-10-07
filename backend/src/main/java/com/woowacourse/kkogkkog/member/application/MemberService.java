package com.woowacourse.kkogkkog.member.application;

import static java.util.stream.Collectors.toList;

import com.woowacourse.kkogkkog.auth.application.dto.MemberUpdateResponse;
import com.woowacourse.kkogkkog.coupon.domain.CouponHistory;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponHistoryRepository;
import com.woowacourse.kkogkkog.infrastructure.dto.GoogleUserInfo;
import com.woowacourse.kkogkkog.infrastructure.dto.SlackUserInfo;
import com.woowacourse.kkogkkog.member.application.dto.MemberHistoryResponse;
import com.woowacourse.kkogkkog.member.application.dto.MemberNicknameUpdateRequest;
import com.woowacourse.kkogkkog.member.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.member.application.dto.MyProfileResponse;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.Nickname;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import com.woowacourse.kkogkkog.member.domain.WorkspaceUser;
import com.woowacourse.kkogkkog.member.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.member.domain.repository.WorkspaceUserRepository;
import com.woowacourse.kkogkkog.member.exception.MemberHistoryNotFoundException;
import com.woowacourse.kkogkkog.member.exception.MemberNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final WorkspaceUserRepository workspaceUserRepository;
    private final CouponHistoryRepository memberHistoryRepository;

    public MemberService(MemberRepository memberRepository,
                         WorkspaceUserRepository workspaceUserRepository,
                         CouponHistoryRepository couponHistoryRepository) {
        this.memberRepository = memberRepository;
        this.workspaceUserRepository = workspaceUserRepository;
        this.memberHistoryRepository = couponHistoryRepository;
    }

    public boolean existsMember(SlackUserInfo userInfo) {
        String email = userInfo.getEmail();
        return memberRepository.findByEmail(email)
            .isPresent();
    }

    public Long save(SlackUserInfo userInfo, Workspace workspace, String nickname) {
        String userId = userInfo.getUserId();
        String email = userInfo.getEmail();
        String imageUrl = userInfo.getPicture();

        Member newMember = memberRepository.save(
            new Member(userId, workspace, new Nickname(nickname), email, imageUrl));
        workspaceUserRepository.save(
            new WorkspaceUser(newMember, userId, workspace, nickname, email, imageUrl));
        return newMember.getId();
    }

    public Long save(GoogleUserInfo userInfo, String nickname) {
        String email = userInfo.getEmail();
        String imageUrl = userInfo.getPicture();
        Member savedMember = memberRepository.save(
            new Member(nickname, email, imageUrl)
        );
        return savedMember.getId();
    }

    public MemberUpdateResponse update(SlackUserInfo userInfo, Workspace workspace) {
        String userId = userInfo.getUserId();
        Optional<WorkspaceUser> workspaceUser = workspaceUserRepository.findByUserId(userId);
        if (workspaceUser.isPresent()) {
            return updateExistingMember(userInfo, workspaceUser.get(), workspace);
        }
        Member member = memberRepository.findByEmail(userInfo.getEmail())
            .orElseThrow(MemberNotFoundException::new);
        return integrateNewWorkspaceUser(userInfo, member, workspace);
    }

    private MemberUpdateResponse updateExistingMember(SlackUserInfo userInfo,
                                                      WorkspaceUser workspaceUser,
                                                      Workspace workspace) {
        workspaceUser.updateDisplayName(userInfo.getName());
        workspaceUser.updateImageURL(userInfo.getPicture());

        Member member = workspaceUser.getMasterMember();
        member.updateMainSlackUserId(userInfo.getUserId());
        member.updateImageURL(userInfo.getPicture());
        member.updateWorkspace(workspace);
        return new MemberUpdateResponse(member.getId(), false);
    }

    private MemberUpdateResponse integrateNewWorkspaceUser(SlackUserInfo userInfo,
                                                           Member existingMember,
                                                           Workspace workspace) {
        existingMember.updateMainSlackUserId(userInfo.getUserId());
        existingMember.updateImageURL(userInfo.getPicture());
        existingMember.updateWorkspace(workspace);
        workspaceUserRepository.save(
            new WorkspaceUser(null, existingMember, userInfo.getUserId(), workspace,
                userInfo.getName(), userInfo.getEmail(), userInfo.getPicture()));
        return new MemberUpdateResponse(existingMember.getId(), false);
    }

    @Transactional(readOnly = true)
    public MyProfileResponse findById(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);
        Long unreadHistoryCount = memberHistoryRepository
            .countByHostMemberAndIsReadFalse(findMember);

        return MyProfileResponse.of(findMember, unreadHistoryCount);
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findAll() {
        return memberRepository.findAll().stream()
            .map(MemberResponse::of)
            .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findByNickname(String searchName) {
        List<Member> members = memberRepository.findByNickname(searchName);

        return members.stream()
            .map(MemberResponse::of)
            .collect(Collectors.toList());
    }

    public void updateNickname(MemberNicknameUpdateRequest memberNicknameUpdateRequest) {
        Member member = memberRepository.findById(memberNicknameUpdateRequest.getMemberId())
            .orElseThrow(MemberNotFoundException::new);

        member.updateNickname(memberNicknameUpdateRequest.getNickname());
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
        CouponHistory memberHistory = memberHistoryRepository.findById(memberHistoryId)
            .orElseThrow(MemberHistoryNotFoundException::new);

        memberHistory.updateIsRead();
    }

    public void updateAllIsReadMemberHistories(Long memberId) {
        Member foundMember = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);

        List<CouponHistory> couponHistories = memberHistoryRepository
            .findAllByHostMemberOrderByCreatedTimeDesc(foundMember);
        for (CouponHistory couponHistory : couponHistories) {
            couponHistory.updateIsRead();
        }
    }
}
