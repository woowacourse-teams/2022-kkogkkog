package com.woowacourse.kkogkkog.legacy_coupon.application;

import com.woowacourse.kkogkkog.coupon.domain.CouponEventType;
import com.woowacourse.kkogkkog.infrastructure.event.PushAlarmPublisher;
import com.woowacourse.kkogkkog.legacy_coupon.application.dto.CouponDetailResponse;
import com.woowacourse.kkogkkog.legacy_coupon.application.dto.CouponReservationResponse;
import com.woowacourse.kkogkkog.legacy_coupon.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.legacy_coupon.application.dto.CouponSaveRequest;
import com.woowacourse.kkogkkog.legacy_coupon.domain.LegacyCoupon;
import com.woowacourse.kkogkkog.legacy_coupon.domain.query.CouponDetailData;
import com.woowacourse.kkogkkog.legacy_coupon.domain.query.CouponQueryRepository;
import com.woowacourse.kkogkkog.legacy_coupon.domain.repository.LegacyCouponRepository;
import com.woowacourse.kkogkkog.legacy_member.domain.LegacyMemberHistory;
import com.woowacourse.kkogkkog.legacy_member.domain.repository.MemberHistoryRepository;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.member.exception.MemberNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class LegacyCouponService {

    private final MemberRepository memberRepository;
    private final LegacyCouponRepository couponRepository;
    private final CouponQueryRepository couponQueryRepository;
    private final MemberHistoryRepository memberHistoryRepository;

    private final PushAlarmPublisher publisher;

    public LegacyCouponService(MemberRepository memberRepository,
                               LegacyCouponRepository couponRepository,
                               CouponQueryRepository couponQueryRepository,
                               MemberHistoryRepository memberHistoryRepository,
                               PushAlarmPublisher applicationEventPublisher) {
        this.memberRepository = memberRepository;
        this.couponRepository = couponRepository;
        this.couponQueryRepository = couponQueryRepository;
        this.memberHistoryRepository = memberHistoryRepository;
        this.publisher = applicationEventPublisher;
    }

    @Transactional(readOnly = true)
    public List<CouponReservationResponse> findAllBySender(Long memberId) {
        Member member = findMember(memberId);
        return couponQueryRepository.findAllBySender(member).stream()
            .map(it -> CouponReservationResponse.of(it, "SENT"))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CouponReservationResponse> findAllByReceiver(Long memberId) {
        Member member = findMember(memberId);
        return couponQueryRepository.findAllByReceiver(member).stream()
            .map(it -> CouponReservationResponse.of(it, "RECEIVED"))
            .collect(Collectors.toList());
    }

    public List<CouponResponse> save(CouponSaveRequest request) {
        Member sender = findMember(request.getSenderId());
        List<Member> receivers = findReceivers(request.getReceiverIds());
        List<LegacyCoupon> coupons = request.toEntities(sender, receivers);

        List<LegacyCoupon> saveCoupons = couponRepository.saveAll(coupons);
        for (LegacyCoupon savedCoupon : saveCoupons) {
            LegacyMemberHistory memberHistory = saveMemberHistory(savedCoupon);
            publisher.publishEvent(memberHistory);
        }

        return saveCoupons.stream()
            .map(CouponResponse::of)
            .collect(Collectors.toList());
    }

    private LegacyMemberHistory saveMemberHistory(LegacyCoupon savedCoupon) {
        LegacyMemberHistory memberHistory = new LegacyMemberHistory(null, savedCoupon.getReceiver(),
            savedCoupon.getSender(), savedCoupon.getId(), savedCoupon.getCouponType(),
            CouponEventType.INIT, null, null);
        memberHistoryRepository.save(memberHistory);
        return memberHistory;
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

    @Transactional(readOnly = true)
    public CouponDetailResponse find(Long couponId) {
        CouponDetailData couponDetail = couponQueryRepository.findCouponWithMeetingDate(couponId);
        List<LegacyMemberHistory> memberHistories = memberHistoryRepository.findAllByCouponIdOrderByCreatedTimeDesc(couponId);
        return CouponDetailResponse.of(couponDetail, memberHistories);
    }
}
