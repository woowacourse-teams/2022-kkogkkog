package com.woowacourse.kkogkkog.application;

import com.woowacourse.kkogkkog.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.domain.Coupon;
import com.woowacourse.kkogkkog.domain.CouponStatus;
import com.woowacourse.kkogkkog.domain.CouponType;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.exception.coupon.CouponNotFoundException;
import com.woowacourse.kkogkkog.exception.member.MemberNotFoundException;
import com.woowacourse.kkogkkog.presentation.dto.CouponCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CouponService {

    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;

    public CouponService(CouponRepository couponRepository, MemberRepository memberRepository) {
        this.couponRepository = couponRepository;
        this.memberRepository = memberRepository;
    }

    public Long save(CouponCreateRequest couponCreateRequest) {
        Coupon coupon = saveCoupon(couponCreateRequest);
        Coupon savedCoupon = couponRepository.save(coupon);

        return savedCoupon.getId();
    }

    private Coupon saveCoupon(CouponCreateRequest couponCreateRequest) {
        Member sender = findMember(couponCreateRequest.getSenderId());
        Member receiver = findMember(couponCreateRequest.getReceiverId());
        String modifier = couponCreateRequest.getModifier();
        String message = couponCreateRequest.getMessage();
        String backgroundColor = couponCreateRequest.getBackgroundColor();
        CouponType couponType = CouponType.of(couponCreateRequest.getCouponType());
        CouponStatus couponStatus = CouponStatus.READY;

        return new Coupon(sender, receiver, modifier, message, backgroundColor, couponType, couponStatus);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public CouponResponse findById(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(CouponNotFoundException::new);

        return CouponResponse.of(coupon);
    }
}
