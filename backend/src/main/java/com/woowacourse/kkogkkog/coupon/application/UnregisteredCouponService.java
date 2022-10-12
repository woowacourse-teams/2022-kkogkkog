package com.woowacourse.kkogkkog.coupon.application;

import static com.woowacourse.kkogkkog.coupon.domain.UnregisteredCouponStatus.REGISTERED;

import com.woowacourse.kkogkkog.coupon.application.dto.UnregisteredCouponDetailResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.UnregisteredCouponResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.UnregisteredCouponSaveRequest;
import com.woowacourse.kkogkkog.coupon.domain.CouponUnregisteredCoupon;
import com.woowacourse.kkogkkog.coupon.domain.UnregisteredCoupon;
import com.woowacourse.kkogkkog.coupon.domain.UnregisteredCouponStatus;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponUnregisteredCouponRepository;
import com.woowacourse.kkogkkog.coupon.domain.repository.UnregisteredCouponRepository;
import com.woowacourse.kkogkkog.coupon.exception.UnregisteredCouponNotAccessibleException;
import com.woowacourse.kkogkkog.coupon.exception.UnregisteredCouponNotFoundException;
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

    private final UnregisteredCouponRepository unregisteredCouponRepository;
    private final CouponUnregisteredCouponRepository couponUnregisteredCouponRepository;
    private final MemberRepository memberRepository;

    public UnregisteredCouponService(UnregisteredCouponRepository unregisteredCouponRepository,
                                     CouponUnregisteredCouponRepository couponUnregisteredCouponRepository,
                                     MemberRepository memberRepository) {
        this.unregisteredCouponRepository = unregisteredCouponRepository;
        this.couponUnregisteredCouponRepository = couponUnregisteredCouponRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public List<UnregisteredCouponResponse> findAllBySender(Long memberId) {
        Member sender = findMember(memberId);
        return unregisteredCouponRepository.findAllBySender(sender).stream()
            .map(UnregisteredCouponResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UnregisteredCouponResponse> findAllBySender(Long memberId, String couponStatus) {
        Member member = findMember(memberId);
        UnregisteredCouponStatus status = UnregisteredCouponStatus.valueOf(couponStatus);
        List<UnregisteredCoupon> unregisteredCoupons = unregisteredCouponRepository.findAllBySender(member, status);
        if (REGISTERED.equals(status)) {
            return findAllBySenderWhereRegistered(unregisteredCoupons);
        }
        return unregisteredCoupons.stream()
            .map(UnregisteredCouponResponse::of)
            .collect(Collectors.toList());
    }

    private List<UnregisteredCouponResponse> findAllBySenderWhereRegistered(List<UnregisteredCoupon> unregisteredCoupons) {
        List<CouponUnregisteredCoupon> couponUnregisteredCoupons = couponUnregisteredCouponRepository.findAllByUnregisteredCoupons(
            unregisteredCoupons);
        return toUnregisteredCouponResponse(couponUnregisteredCoupons);
    }

    private List<UnregisteredCouponResponse> toUnregisteredCouponResponse(List<CouponUnregisteredCoupon> couponUnregisteredCoupons) {
        return couponUnregisteredCoupons.stream()
            .map(UnregisteredCouponResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UnregisteredCouponDetailResponse findById(Long memberId, Long unregisteredCouponId) {
        Member member = findMember(memberId);
        UnregisteredCoupon unregisteredCoupon = findUnregisteredCoupon(unregisteredCouponId);
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
        Member sender = findMember(request.getSenderId());
        List<UnregisteredCoupon> unregisteredCoupons = request.toEntities(sender);
        return unregisteredCouponRepository.saveAll(unregisteredCoupons).stream()
            .map(UnregisteredCouponResponse::of)
            .collect(Collectors.toList());
    }

    public void delete(Long memberId, Long unregisteredCouponId) {
        Member member = findMember(memberId);
        UnregisteredCoupon unregisteredCoupon = findUnregisteredCoupon(unregisteredCouponId);
        if (unregisteredCoupon.isNotSender(member)) {
            throw new UnregisteredCouponNotAccessibleException();
        }
        unregisteredCouponRepository.delete(unregisteredCoupon);
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
