package com.woowacourse.kkogkkog.coupon.application;

import com.woowacourse.kkogkkog.coupon.application.dto.CouponDetailResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponStatusRequest;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponSaveRequest;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.coupon.domain.CouponHistory;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponHistoryRepository;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.coupon.exception.CouponNotFoundException;
import com.woowacourse.kkogkkog.infrastructure.event.PushAlarmEvent;
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
            .peek(it -> saveCouponHistory(CouponHistory.ofNew(it)))
            .map(CouponResponse::of)
            .collect(Collectors.toList());
    }

    public void update(CouponStatusRequest request) {
        CouponEvent event = request.getEvent();
        Member loginMember = findMember(request.getMemberId());
        Coupon coupon = findCoupon(request.getCouponId());
        coupon.changeState(event, loginMember);
        saveCouponHistory(CouponHistory.of(loginMember, coupon, event, request.getMessage()));
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

    private void saveCouponHistory(CouponHistory couponHistory) {
        couponHistory = couponHistoryRepository.save(couponHistory);
        publisher.publishEvent(PushAlarmEvent.of(couponHistory));
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);
    }
}
