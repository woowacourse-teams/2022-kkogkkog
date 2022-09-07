package com.woowacourse.kkogkkog.coupon2.application;

import com.woowacourse.kkogkkog.coupon.exception.CouponNotFoundException;
import com.woowacourse.kkogkkog.coupon2.application.dto.CouponDetailResponse;
import com.woowacourse.kkogkkog.coupon2.application.dto.CouponEventRequest;
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
@Service(value = "couponService2")
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
        Coupon coupon = findCoupon(couponId);
        List<CouponHistory> couponHistories = couponHistoryRepository.findAllByCouponIdOrderByCreatedTimeDesc(
            couponId);
        return CouponDetailResponse.of(coupon, couponHistories);
    }

    @Transactional(readOnly = true)
    public List<CouponResponse> findAllBySender(Long memberId) {
        Member member = findMember(memberId);
        return couponRepository.findAllBySender(member).stream()
            .map(CouponResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CouponResponse> findAllByReceiver(Long memberId) {
        Member member = findMember(memberId);
        return couponRepository.findAllByReceiver(member).stream()
            .map(CouponResponse::of)
            .collect(Collectors.toList());
    }

    public List<CouponResponse> save(CouponSaveRequest request) {
        Member sender = findMember(request.getSenderId());
        List<Member> receivers = findReceivers(request.getReceiverIds());
        List<Coupon> coupons = request.toEntities(sender, receivers);
        return couponRepository.saveAll(coupons).stream()
            .peek(this::saveCouponHistory)
            .map(CouponResponse::of)
            .collect(Collectors.toList());
    }

    public void update(CouponEventRequest request) {
        Member loginMember = findMember(request.getMemberId());
        Coupon coupon = findCoupon(request.getCouponId());
        coupon.changeState(request.toEvent(), loginMember);
        saveCouponHistory(coupon);
    }

    private Coupon findCoupon(Long couponId) {
        return couponRepository.findById(couponId)
            .orElseThrow(CouponNotFoundException::new);
    }

    private List<Member> findReceivers(List<Long> memberIds) {
        List<Member> foundMembers = memberRepository.findAllById(memberIds);
        if (memberIds.size() != foundMembers.size()) {
            throw new MemberNotFoundException();
        }
        return foundMembers;
    }

    private void saveCouponHistory(Coupon savedCoupon) {
        CouponHistory couponHistory = CouponHistory.ofNew(savedCoupon);
        couponHistory = couponHistoryRepository.save(couponHistory);
        publisher.publishEvent(PushAlarmEvent2.of(couponHistory));
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);
    }
}
