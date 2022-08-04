package com.woowacourse.kkogkkog.application;

import com.woowacourse.kkogkkog.application.dto.CouponChangeStatusRequest;
import com.woowacourse.kkogkkog.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.application.dto.CouponSaveRequest;
import com.woowacourse.kkogkkog.domain.Coupon;
import com.woowacourse.kkogkkog.domain.CouponEvent;
import com.woowacourse.kkogkkog.domain.CouponStatus;
import com.woowacourse.kkogkkog.domain.CouponType;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.MemberHistory;
import com.woowacourse.kkogkkog.domain.Workspace;
import com.woowacourse.kkogkkog.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.domain.repository.MemberHistoryRepository;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.domain.repository.WorkspaceRepository;
import com.woowacourse.kkogkkog.exception.InvalidRequestException;
import com.woowacourse.kkogkkog.exception.coupon.CouponNotFoundException;
import com.woowacourse.kkogkkog.exception.member.MemberNotFoundException;
import com.woowacourse.kkogkkog.infrastructure.SlackClient;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CouponService {

    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;
    private final WorkspaceRepository workspaceRepository;
    private final MemberHistoryRepository memberHistoryRepository;
    private final SlackClient slackClient;

    public CouponService(CouponRepository couponRepository, MemberRepository memberRepository,
                         WorkspaceRepository workspaceRepository,
                         MemberHistoryRepository memberHistoryRepository,
                         SlackClient slackClient) {
        this.couponRepository = couponRepository;
        this.memberRepository = memberRepository;
        this.workspaceRepository = workspaceRepository;
        this.memberHistoryRepository = memberHistoryRepository;
        this.slackClient = slackClient;
    }

    @Transactional(readOnly = true)
    public CouponResponse findById(Long couponId) {
        Coupon coupon = findCoupon(couponId);

        return CouponResponse.of(coupon);
    }

    @Transactional(readOnly = true)
    public List<CouponResponse> findAllBySender(Long senderId) {
        return couponRepository.findAllBySender(findMember(senderId))
            .stream()
            .map(CouponResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CouponResponse> findAllByReceiver(Long receiverId) {
        return couponRepository.findAllByReceiver(findMember(receiverId))
            .stream()
            .map(CouponResponse::of)
            .collect(Collectors.toList());
    }

    public List<CouponResponse> save(CouponSaveRequest couponSaveRequest) {
        List<Coupon> coupons = toCoupons(couponSaveRequest);
        List<Coupon> savedCoupons = couponRepository.saveAll(coupons);

        for (Coupon savedCoupon : savedCoupons) {
            MemberHistory memberHistory = saveMemberHistory(savedCoupon.getReceiver(),
                savedCoupon.getSender(), savedCoupon, CouponEvent.INIT);
            sendNotification(memberHistory);
        }
        return savedCoupons.stream()
            .map(CouponResponse::of)
            .collect(Collectors.toList());
    }

    private List<Coupon> toCoupons(CouponSaveRequest couponSaveRequest) {
        Member sender = findMember(couponSaveRequest.getSenderId());
        List<Member> receivers = findMembers(couponSaveRequest.getReceivers());
        String modifier = couponSaveRequest.getModifier();
        String message = couponSaveRequest.getMessage();
        String backgroundColor = couponSaveRequest.getBackgroundColor();
        CouponType couponType = CouponType.valueOf(couponSaveRequest.getCouponType());
        CouponStatus couponStatus = CouponStatus.READY;

        return receivers.stream()
            .map(it -> new Coupon(sender, it, modifier, message, backgroundColor, couponType,
                couponStatus))
            .collect(Collectors.toList());
    }

    private List<Member> findMembers(List<Long> memberIds) {
        List<Member> receivers = memberRepository.findAllById(memberIds);
        if (memberIds.size() != receivers.size()) {
            throw new MemberNotFoundException();
        }
        return receivers;
    }

    public void changeStatus(CouponChangeStatusRequest couponChangeStatusRequest) {
        Member loginMember = findMember(couponChangeStatusRequest.getLoginMemberId());
        Coupon coupon = findCoupon(couponChangeStatusRequest.getCouponId());
        coupon.changeStatus(couponChangeStatusRequest.getEvent(), loginMember);

        if (couponChangeStatusRequest.getEvent() == CouponEvent.REQUEST) {
            if (couponChangeStatusRequest.getMeetingDate() == null) {
                throw new InvalidRequestException("사용요청을 보낼땐 약속날짜가 필요합니다.");
            }
            coupon.updateMeetingDate(couponChangeStatusRequest.getMeetingDate());
        }

        Member hostMember = coupon.getOppositeMember(loginMember);
        MemberHistory memberHistory = saveMemberHistory(hostMember, loginMember, coupon,
            couponChangeStatusRequest.getEvent());
        sendNotification(memberHistory);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);
    }

    private Coupon findCoupon(Long couponId) {
        return couponRepository.findById(couponId)
            .orElseThrow(CouponNotFoundException::new);
    }

    private MemberHistory saveMemberHistory(Member hostMember, Member targetMember, Coupon coupon,
                                            CouponEvent couponEvent) {
        MemberHistory memberHistory = new MemberHistory(null, hostMember, targetMember,
            coupon.getId(), coupon.getCouponType(), couponEvent, coupon.getMeetingDate());
        return memberHistoryRepository.save(memberHistory);
    }

    private void sendNotification(MemberHistory memberHistory) {
        Member hostMember = memberHistory.getHostMember();
        Optional<Workspace> workspace = workspaceRepository.findByWorkspaceId(
            hostMember.getWorkspaceId());
        if (workspace.isPresent()) {
            String accessToken = workspace.get().getAccessToken();
            String hostMemberId = hostMember.getUserId();
            String message = memberHistory.toNoticeMessage();
            slackClient.requestPushAlarm(accessToken, hostMemberId, message);
        }
    }
}
