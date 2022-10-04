package com.woowacourse.kkogkkog.coupon.application;

import com.woowacourse.kkogkkog.coupon.application.dto.UnregisteredCouponDetailResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.UnregisteredCouponResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.UnregisteredCouponSaveRequest;
import com.woowacourse.kkogkkog.coupon.domain.UnregisteredCoupon;
import com.woowacourse.kkogkkog.coupon.domain.repository.UnregisteredCouponRepository;
import com.woowacourse.kkogkkog.coupon.exception.UnregisteredCouponNotFoundException;
import com.woowacourse.kkogkkog.coupon.exception.UnregisteredCouponQuantityExcessException;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.member.exception.MemberNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UnregisteredCouponService {

    private static final int MINIMUM_QUANTITY = 0;
    private static final int MAXIMUM_QUANTITY = 5;

    private final UnregisteredCouponRepository unregisteredCouponRepository;
    private final MemberRepository memberRepository;

    public UnregisteredCouponService(UnregisteredCouponRepository unregisteredCouponRepository,
                                     MemberRepository memberRepository) {
        this.unregisteredCouponRepository = unregisteredCouponRepository;
        this.memberRepository = memberRepository;
    }

    public List<UnregisteredCouponResponse> findAllBySender(Long memberId) {
        Member sender = findMember(memberId);
        return unregisteredCouponRepository.findAllBySender(sender).stream()
            .map(UnregisteredCouponResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UnregisteredCouponDetailResponse findById(Long unregisteredCouponId) {
        UnregisteredCoupon unregisteredCoupon = findUnregisteredCoupon(unregisteredCouponId);
        return UnregisteredCouponDetailResponse.of(unregisteredCoupon);
    }

    @Transactional(readOnly = true)
    public UnregisteredCouponDetailResponse findByCouponCode(String couponCode) {
        UnregisteredCoupon unregisteredCoupon = findUnregisteredCoupon(couponCode);
        return UnregisteredCouponDetailResponse.of(unregisteredCoupon);
    }

    public List<UnregisteredCouponResponse> save(UnregisteredCouponSaveRequest request) {
        int quantity = request.getQuantity();
        if (!canSave(quantity)) {
            throw new UnregisteredCouponQuantityExcessException();
        }
        Member sender = findMember(request.getSenderId());
        List<UnregisteredCoupon> unregisteredCoupons = request.toEntities(sender);
        return unregisteredCouponRepository.saveAll(unregisteredCoupons).stream()
            .map(UnregisteredCouponResponse::of)
            .collect(Collectors.toList());
    }

    private boolean canSave(int quantity) {
        return MINIMUM_QUANTITY < quantity && quantity <= MAXIMUM_QUANTITY;
    }

    public void deleteByCouponCode(String couponCode) {
        unregisteredCouponRepository.findByCouponCode(couponCode)
            .ifPresent(unregisteredCouponRepository::delete);
    }

    private UnregisteredCoupon findUnregisteredCoupon(Long unregisteredCouponId) {
        return unregisteredCouponRepository.findById(unregisteredCouponId)
            .orElseThrow(UnregisteredCouponNotFoundException::new);
    }

    private UnregisteredCoupon findUnregisteredCoupon(String couponCode) {
        return unregisteredCouponRepository.findByCouponCode(couponCode)
            .orElseThrow(UnregisteredCouponNotFoundException::new);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);
    }
}
