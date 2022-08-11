package com.woowacourse.kkogkkog.coupon.application;

import com.woowacourse.kkogkkog.coupon.application.dto.CouponReservationResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponSaveRequest;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.coupon.domain.query.CouponQueryRepository;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.MemberHistory;
import com.woowacourse.kkogkkog.domain.Workspace;
import com.woowacourse.kkogkkog.domain.repository.MemberHistoryRepository;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.exception.member.MemberNotFoundException;
import com.woowacourse.kkogkkog.infrastructure.SlackClient;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CouponService {

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final CouponQueryRepository couponQueryRepository;
    private final MemberHistoryRepository memberHistoryRepository;
    private final SlackClient slackClient;

    public CouponService(MemberRepository memberRepository,
                         CouponRepository couponRepository,
                         CouponQueryRepository couponQueryRepository,
                         MemberHistoryRepository memberHistoryRepository,
                         SlackClient slackClient) {
        this.memberRepository = memberRepository;
        this.couponRepository = couponRepository;
        this.couponQueryRepository = couponQueryRepository;
        this.memberHistoryRepository = memberHistoryRepository;
        this.slackClient = slackClient;
    }

    @Transactional(readOnly = true)
    public List<CouponReservationResponse> findAllBySender(Long senderId) {
        Member sender = findMember(senderId);
        return couponQueryRepository.findAllBySender(sender).stream()
            .map(CouponReservationResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CouponReservationResponse> findAllByReceiver(Long receiverId) {
        Member sender = findMember(receiverId);
        return couponQueryRepository.findAllByReceiver(sender).stream()
            .map(CouponReservationResponse::of)
            .collect(Collectors.toList());
    }

    public List<CouponResponse> save(CouponSaveRequest request) {
        Member sender = findMember(request.getSenderId());
        List<Member> receivers = findReceivers(request.getReceiverIds());

        List<Coupon> coupons = request.toEntities(sender, receivers);

        List<Coupon> saveCoupons = couponRepository.saveAll(coupons);
        for (Coupon savedCoupon : saveCoupons) {
            MemberHistory memberHistory = saveMemberHistory(savedCoupon.getReceiver(),
                savedCoupon.getSender(), savedCoupon, CouponEvent.INIT);
            sendNotification(memberHistory);
        }
        return saveCoupons.stream()
            .map(CouponResponse::of)
            .collect(Collectors.toList());
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);
    }

    private List<Member> findReceivers(List<Long> memberIds) {
        List<Member> foundMembers = memberRepository.findAllById(memberIds);
        if (memberIds.size() != foundMembers.size()) {
            throw new MemberNotFoundException();
        }

        return foundMembers;
    }

    private MemberHistory saveMemberHistory(Member hostMember, Member targetMember, Coupon coupon,
                                            CouponEvent couponEvent) {
        MemberHistory memberHistory = new MemberHistory(null, hostMember, targetMember,
            coupon.getId(), coupon.getCouponType(), couponEvent, null);
        return memberHistoryRepository.save(memberHistory);
    }

    private void sendNotification(MemberHistory memberHistory) {
        Member hostMember = memberHistory.getHostMember();
        Workspace workspace = hostMember.getWorkspace();
        String accessToken = workspace.getAccessToken();
        if (accessToken == null || memberHistory.shouldNotSendPushAlarm()) {
            return;
        }
        String hostMemberId = hostMember.getUserId();
        String message = memberHistory.toNoticeMessage();
        slackClient.requestPushAlarm(accessToken, hostMemberId, message);
    }
}
