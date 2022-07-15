package com.woowacourse.kkogkkog.application;

import com.woowacourse.kkogkkog.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.application.dto.CouponSaveRequest;
import com.woowacourse.kkogkkog.domain.Coupon;
import com.woowacourse.kkogkkog.domain.CouponStatus;
import com.woowacourse.kkogkkog.domain.CouponType;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.exception.coupon.CouponNotFoundException;
import com.woowacourse.kkogkkog.exception.member.MemberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CouponService {

    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;

    public CouponService(CouponRepository couponRepository, MemberRepository memberRepository) {
        this.couponRepository = couponRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public CouponResponse findById(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(CouponNotFoundException::new);

        return CouponResponse.of(coupon);
    }

    @Transactional(readOnly = true)
    public List<CouponResponse> findAllBySender(Long senderId) {
        return couponRepository.findAllBySender(findMember(senderId))
                .stream()
                .map(CouponResponse::of)
                .collect(Collectors.toList());
    }

    public List<CouponResponse> save(CouponSaveRequest couponSaveRequest) {
        List<Coupon> coupons = toCoupons(couponSaveRequest);
        List<Coupon> savedCoupons = couponRepository.saveAll(coupons);
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
                .map(it -> new Coupon(sender, it, modifier, message, backgroundColor, couponType, couponStatus))
                .collect(Collectors.toList());
    }

    private List<Member> findMembers(List<Long> memberIds) {
        List<Member> receivers = memberRepository.findAllById(memberIds);
        if (memberIds.size() != receivers.size()) {
            throw new MemberNotFoundException();
        }
        return receivers;
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }
}
