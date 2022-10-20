package com.woowacourse.kkogkkog.lazycoupon.application;

import static com.woowacourse.kkogkkog.lazycoupon.domain.LazyCouponEventType.EXPIRE;
import static com.woowacourse.kkogkkog.lazycoupon.domain.LazyCouponStatus.ISSUED;

import com.woowacourse.kkogkkog.coupon.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponHistory;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponHistoryRepository;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.coupon.presentation.dto.RegisterCouponCodeRequest;
import com.woowacourse.kkogkkog.infrastructure.event.PushAlarmPublisher;
import com.woowacourse.kkogkkog.lazycoupon.domain.LazyCouponStatus;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.lazycoupon.application.dto.LazyCouponResponse;
import com.woowacourse.kkogkkog.lazycoupon.application.dto.LazyCouponSaveRequest;
import com.woowacourse.kkogkkog.lazycoupon.domain.CouponLazyCoupon;
import com.woowacourse.kkogkkog.lazycoupon.domain.LazyCoupon;
import com.woowacourse.kkogkkog.lazycoupon.domain.repository.CouponLazyCouponRepository;
import com.woowacourse.kkogkkog.lazycoupon.exception.LazyCouponNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class LazyCouponService {

    private final CouponLazyCouponRepository couponLazyCouponRepository;
    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;
    private final CouponHistoryRepository couponHistoryRepository;
    private final PushAlarmPublisher pushAlarmPublisher;

    @Transactional(readOnly = true)
    public List<LazyCouponResponse> findAllBySender(Long memberId, String lazyCouponStatus) {
        Member sender = memberRepository.get(memberId);
        LazyCouponStatus status = LazyCouponStatus.valueOf(lazyCouponStatus);
        List<CouponLazyCoupon> couponLazyCoupons = couponLazyCouponRepository.findAllBySender(sender, status);
        if (ISSUED.equals(status)) {
            expireCouponLazyCoupons(couponLazyCoupons);
        }
        return couponLazyCoupons.stream()
            .map(LazyCouponResponse::of)
            .collect(Collectors.toList());
    }

    private void expireCouponLazyCoupons(List<CouponLazyCoupon> couponLazyCoupons) {
        for (CouponLazyCoupon couponLazyCoupon : couponLazyCoupons) {
            expireLazyCoupon(couponLazyCoupon.getLazyCoupon());
        }
    }

    @Transactional(readOnly = true)
    public LazyCouponResponse findById(Long memberId, Long lazyCouponId) {
        Member member = memberRepository.get(memberId);
        CouponLazyCoupon couponLazyCoupon = findLazyCoupon(lazyCouponId);
        couponLazyCoupon.validateSender(member);
        return LazyCouponResponse.of(couponLazyCoupon);
    }

    @Transactional(readOnly = true)
    public LazyCouponResponse findByCouponCode(String couponCode) {
        CouponLazyCoupon couponLazyCoupon = findLazyCoupon(couponCode);
        return LazyCouponResponse.of(couponLazyCoupon);
    }

    public List<LazyCouponResponse> save(LazyCouponSaveRequest request) {
        int quantity = request.getQuantity();
        LazyCoupon.validateQuantity(quantity);
        Member sender = memberRepository.get(request.getSenderId());
        List<CouponLazyCoupon> lazyCoupons = request.toEntities(sender);
        return couponLazyCouponRepository.saveAll(lazyCoupons).stream()
            .map(LazyCouponResponse::of)
            .collect(Collectors.toList());
    }

    public CouponResponse saveByCouponCode(Long memberId, RegisterCouponCodeRequest couponCode) {
        Member receiver = memberRepository.get(memberId);
        CouponLazyCoupon couponLazyCoupon = findLazyCoupon(couponCode.getCouponCode());
        Coupon coupon = couponRepository.save(couponLazyCoupon.registerCoupon(receiver));
        saveCouponHistory(CouponHistory.ofNewByCouponCode(coupon));
        return CouponResponse.of(coupon);
    }

    public void delete(Long memberId, Long lazyCouponId) {
        Member member = memberRepository.get(memberId);
        CouponLazyCoupon couponLazyCoupon = findLazyCoupon(lazyCouponId);
        couponLazyCoupon.validateSender(member);
        couponLazyCouponRepository.delete(couponLazyCoupon);
    }

    private CouponLazyCoupon findLazyCoupon(Long id) {
        CouponLazyCoupon couponLazyCoupon = couponLazyCouponRepository.findByLazyCouponId(id)
            .orElseThrow(LazyCouponNotFoundException::new);
        expireLazyCoupon(couponLazyCoupon.getLazyCoupon());
        return couponLazyCoupon;
    }

    private CouponLazyCoupon findLazyCoupon(String couponCode) {
        CouponLazyCoupon couponLazyCoupon = couponLazyCouponRepository.findByLazyCouponCouponCode(couponCode)
            .orElseThrow(LazyCouponNotFoundException::new);
        expireLazyCoupon(couponLazyCoupon.getLazyCoupon());
        return couponLazyCoupon;
    }

    private void expireLazyCoupon(LazyCoupon lazyCoupon) {
        lazyCoupon.changeStatus(EXPIRE);
    }

    private void saveCouponHistory(CouponHistory couponHistory) {
        couponHistory = couponHistoryRepository.save(couponHistory);
        pushAlarmPublisher.publishEvent(couponHistory);
    }
}
