package com.woowacourse.kkogkkog.coupon.application;

import com.woowacourse.kkogkkog.coupon.application.dto.UnregisteredCouponDetailResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.UnregisteredCouponResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.UnregisteredCouponSaveRequest;
import com.woowacourse.kkogkkog.coupon.domain.UnregisteredCoupon;
import com.woowacourse.kkogkkog.coupon.domain.UnregisteredCouponStatus;
import com.woowacourse.kkogkkog.coupon.domain.repository.UnregisteredCouponRepository;
import com.woowacourse.kkogkkog.coupon.exception.UnregisteredCouponNotAccessibleException;
import com.woowacourse.kkogkkog.coupon.exception.UnregisteredCouponNotFoundException;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.repository.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UnregisteredCouponService {

    private final UnregisteredCouponRepository unregisteredCouponRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<UnregisteredCouponResponse> findAllBySender(Long memberId) {
        Member sender = memberRepository.get(memberId);
        return unregisteredCouponRepository.findAllBySender(sender).stream()
            .map(UnregisteredCouponResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UnregisteredCouponResponse> findAllBySender(Long memberId, String unregisteredCouponStatus) {
        Member sender = memberRepository.get(memberId);
        UnregisteredCouponStatus status = UnregisteredCouponStatus.valueOf(unregisteredCouponStatus);
        return unregisteredCouponRepository.findAllBySender(sender, status).stream()
            .map(UnregisteredCouponResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UnregisteredCouponDetailResponse findById(Long memberId, Long unregisteredCouponId) {
        Member member = memberRepository.get(memberId);
        UnregisteredCoupon unregisteredCoupon = unregisteredCouponRepository.get(unregisteredCouponId);
        if (unregisteredCoupon.isNotSender(member)) {
            throw new UnregisteredCouponNotAccessibleException();
        }
        return UnregisteredCouponDetailResponse.of(unregisteredCoupon);
    }

    @Transactional(readOnly = true)
    public UnregisteredCouponDetailResponse findByCouponCode(String couponCode) {
        UnregisteredCoupon unregisteredCoupon = findUnregisteredCoupon(couponCode);
        return UnregisteredCouponDetailResponse.of(unregisteredCoupon);
    }

    public List<UnregisteredCouponResponse> save(UnregisteredCouponSaveRequest request) {
        int quantity = request.getQuantity();
        UnregisteredCoupon.validateQuantity(quantity);
        Member sender = memberRepository.get(request.getSenderId());
        List<UnregisteredCoupon> unregisteredCoupons = request.toEntities(sender);
        return unregisteredCouponRepository.saveAll(unregisteredCoupons).stream()
            .map(UnregisteredCouponResponse::of)
            .collect(Collectors.toList());
    }

    public void delete(Long memberId, Long unregisteredCouponId) {
        Member member = memberRepository.get(memberId);
        UnregisteredCoupon unregisteredCoupon = unregisteredCouponRepository.get(
            unregisteredCouponId);
        if (unregisteredCoupon.isNotSender(member)) {
            throw new UnregisteredCouponNotAccessibleException();
        }
        unregisteredCouponRepository.delete(unregisteredCoupon);
    }

    private UnregisteredCoupon findUnregisteredCoupon(String couponCode) {
        return unregisteredCouponRepository.findByCouponCode(couponCode)
            .orElseThrow(UnregisteredCouponNotFoundException::new);
    }
}
