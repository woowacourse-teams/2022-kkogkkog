package com.woowacourse.kkogkkog.coupon2.application;

import com.woowacourse.kkogkkog.coupon.exception.CouponNotFoundException;
import com.woowacourse.kkogkkog.coupon2.application.dto.CouponDetailResponse;
import com.woowacourse.kkogkkog.coupon2.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.coupon2.application.dto.CouponSaveRequest;
import com.woowacourse.kkogkkog.coupon2.domain.Coupon;
import com.woowacourse.kkogkkog.coupon2.domain.CouponHistory;
import com.woowacourse.kkogkkog.coupon2.domain.repository.CouponHistoryRepository;
import com.woowacourse.kkogkkog.coupon2.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.infrastructure.event.PushAlarmEvent2;
import com.woowacourse.kkogkkog.member.domain.Member;
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
    private final CouponHistoryRepository couponHistoryRepository;

    private final ApplicationEventPublisher publisher;

    public CouponService(MemberRepository memberRepository,
                         CouponRepository couponRepository,
                         CouponHistoryRepository couponHistoryRepository,
                         ApplicationEventPublisher applicationEventPublisher) {
        this.memberRepository = memberRepository;
        this.couponRepository = couponRepository;
        this.couponHistoryRepository = couponHistoryRepository;
        this.publisher = applicationEventPublisher;
    }

    @Transactional(readOnly = true)
    public CouponDetailResponse find(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
            .orElseThrow(CouponNotFoundException::new);
        List<CouponHistory> memberHistories = couponHistoryRepository.findAllByCouponIdOrderByCreatedTimeDesc(
            couponId);
        return CouponDetailResponse.of(coupon, memberHistories);
    }

    public List<CouponResponse> save(CouponSaveRequest request) {
        Member sender = findMember(request.getSenderId());
        List<Member> receivers = findReceivers(request.getReceiverIds());
        List<Coupon> coupons = request.toEntities(sender, receivers);

        List<Coupon> saveCoupons = couponRepository.saveAll(coupons);
        for (Coupon savedCoupon : saveCoupons) {
            CouponHistory couponHistory = saveCouponHistory(savedCoupon);
            publisher.publishEvent(PushAlarmEvent2.of(couponHistory));
        }
        return saveCoupons.stream()
            .map(CouponResponse::of)
            .collect(Collectors.toList());
    }

    private CouponHistory saveCouponHistory(Coupon savedCoupon) {
        CouponHistory memberHistory = CouponHistory.ofNew(savedCoupon);
        return couponHistoryRepository.save(memberHistory);
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
}
