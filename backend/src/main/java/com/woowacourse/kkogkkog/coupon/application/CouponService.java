package com.woowacourse.kkogkkog.coupon.application;

import com.woowacourse.kkogkkog.infrastructure.event.PushAlarmEvent;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponDetailResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponReservationResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponSaveRequest;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.coupon.domain.query.CouponDetailData;
import com.woowacourse.kkogkkog.coupon.domain.query.CouponQueryRepository;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.infrastructure.event.SimplePushAlarmEvent;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.MemberHistory;
import com.woowacourse.kkogkkog.member.domain.repository.MemberHistoryRepository;
import com.woowacourse.kkogkkog.member.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.member.exception.MemberNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CouponService {

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final CouponQueryRepository couponQueryRepository;
    private final MemberHistoryRepository memberHistoryRepository;

    private final ApplicationEventPublisher publisher;

    public CouponService(MemberRepository memberRepository,
                         CouponRepository couponRepository,
                         CouponQueryRepository couponQueryRepository,
                         MemberHistoryRepository memberHistoryRepository,
                         ApplicationEventPublisher applicationEventPublisher) {
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
        List<Coupon> coupons = request.toEntities(sender, receivers);

        List<Coupon> saveCoupons = couponRepository.saveAll(coupons);
        for (Coupon savedCoupon : saveCoupons) {
            MemberHistory memberHistory = saveMemberHistory(savedCoupon);

            publisher.publishEvent(SimplePushAlarmEvent.handle(memberHistory));
        }

        return saveCoupons.stream()
            .map(CouponResponse::of)
            .collect(Collectors.toList());
    }

    private MemberHistory saveMemberHistory(Coupon savedCoupon) {
        MemberHistory memberHistory = new MemberHistory(null, savedCoupon.getReceiver(),
            savedCoupon.getSender(), savedCoupon.getId(), savedCoupon.getCouponType(),
            CouponEvent.INIT, null, null);
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
        List<MemberHistory> memberHistories = memberHistoryRepository.findAllByCouponIdOrderByCreatedTimeDesc(couponId);
        return CouponDetailResponse.of(couponDetail, memberHistories);
    }
}
