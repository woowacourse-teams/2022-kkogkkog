package com.woowacourse.kkogkkog.unregisteredcoupon.application;

import static com.woowacourse.kkogkkog.unregisteredcoupon.domain.UnregisteredCouponEventType.REGISTER;
import static com.woowacourse.kkogkkog.unregisteredcoupon.domain.UnregisteredCouponStatus.EXPIRED;
import static com.woowacourse.kkogkkog.unregisteredcoupon.domain.UnregisteredCouponStatus.ISSUED;
import static com.woowacourse.kkogkkog.unregisteredcoupon.domain.UnregisteredCouponStatus.REGISTERED;

import com.woowacourse.kkogkkog.coupon.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponHistory;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponHistoryRepository;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.coupon.exception.CouponNotFoundException;
import com.woowacourse.kkogkkog.coupon.presentation.dto.RegisterCouponCodeRequest;
import com.woowacourse.kkogkkog.infrastructure.event.PushAlarmPublisher;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.unregisteredcoupon.application.dto.UnregisteredCouponResponse;
import com.woowacourse.kkogkkog.unregisteredcoupon.application.dto.UnregisteredCouponSaveRequest;
import com.woowacourse.kkogkkog.unregisteredcoupon.domain.CouponUnregisteredCoupon;
import com.woowacourse.kkogkkog.unregisteredcoupon.domain.UnregisteredCoupon;
import com.woowacourse.kkogkkog.unregisteredcoupon.domain.UnregisteredCouponStatus;
import com.woowacourse.kkogkkog.unregisteredcoupon.domain.repository.CouponUnregisteredCouponRepository;
import com.woowacourse.kkogkkog.unregisteredcoupon.domain.repository.UnregisteredCouponRepository;
import com.woowacourse.kkogkkog.unregisteredcoupon.exception.UnregisteredCouponNotAccessibleException;
import com.woowacourse.kkogkkog.unregisteredcoupon.exception.UnregisteredCouponNotFoundException;
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
    private final CouponRepository couponRepository;
    private final CouponUnregisteredCouponRepository couponUnregisteredCouponRepository;
    private final MemberRepository memberRepository;
    private final CouponHistoryRepository couponHistoryRepository;
    private final PushAlarmPublisher pushAlarmPublisher;

    @Transactional(readOnly = true)
    public List<UnregisteredCouponResponse> findAllBySender(Long memberId) {
        Member sender = memberRepository.get(memberId);
        List<UnregisteredCouponResponse> responses = findAllBySenderWhereIssuedOrExpired(sender);
        responses.addAll(findAllBySenderWhereRegistered(sender));
        return responses;
    }

    private List<UnregisteredCouponResponse> findAllBySenderWhereIssuedOrExpired(Member sender) {
        return unregisteredCouponRepository.findAllBySender(sender).stream()
            .filter(it -> ISSUED.equals(it.getUnregisteredCouponStatus()) || EXPIRED.equals(it.getUnregisteredCouponStatus()))
            .map(UnregisteredCouponResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UnregisteredCouponResponse> findAllBySender(Long memberId, String unregisteredCouponStatus) {
        Member sender = memberRepository.get(memberId);
        UnregisteredCouponStatus status = UnregisteredCouponStatus.valueOf(unregisteredCouponStatus);
        if (REGISTERED.equals(status)) {
            return findAllBySenderWhereRegistered(sender);
        }
        return unregisteredCouponRepository.findAllBySender(sender, status).stream()
            .map(UnregisteredCouponResponse::of)
            .collect(Collectors.toList());
    }

    private List<UnregisteredCouponResponse> findAllBySenderWhereRegistered(Member sender) {
        return couponUnregisteredCouponRepository.findAllByCouponSender(sender).stream()
            .map(UnregisteredCouponResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UnregisteredCouponResponse findById(Long memberId, Long unregisteredCouponId) {
        Member member = memberRepository.get(memberId);
        UnregisteredCoupon unregisteredCoupon = unregisteredCouponRepository.get(unregisteredCouponId);
        if (unregisteredCoupon.isNotSender(member)) {
            throw new UnregisteredCouponNotAccessibleException();
        }
        if (REGISTERED.equals(unregisteredCoupon.getUnregisteredCouponStatus())) {
            return UnregisteredCouponResponse.of(findCouponUnregisteredCoupon(unregisteredCoupon));
        }
        return UnregisteredCouponResponse.of(unregisteredCoupon);
    }

    @Transactional(readOnly = true)
    public UnregisteredCouponResponse findByCouponCode(String couponCode) {
        UnregisteredCoupon unregisteredCoupon = findUnregisteredCoupon(couponCode);
        if (REGISTERED.equals(unregisteredCoupon.getUnregisteredCouponStatus())) {
            return UnregisteredCouponResponse.of(findCouponUnregisteredCoupon(unregisteredCoupon));
        }
        return UnregisteredCouponResponse.of(unregisteredCoupon);
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

    public CouponResponse saveByCouponCode(Long memberId, RegisterCouponCodeRequest couponCode) {
        Member receiver = memberRepository.get(memberId);
        UnregisteredCoupon unregisteredCoupon = findUnregisteredCoupon(couponCode.getCouponCode());
        unregisteredCoupon.changeStatus(REGISTER);
        Coupon coupon = couponRepository.save(unregisteredCoupon.toCoupon(receiver));
        couponUnregisteredCouponRepository.save(new CouponUnregisteredCoupon(coupon, unregisteredCoupon));
        saveCouponHistory(CouponHistory.ofNewByCouponCode(coupon));
        return CouponResponse.of(coupon);
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

    private CouponUnregisteredCoupon findCouponUnregisteredCoupon(UnregisteredCoupon unregisteredCoupon) {
        return couponUnregisteredCouponRepository.findByUnregisteredCoupon(unregisteredCoupon)
            .orElseThrow(CouponNotFoundException::new);
    }

    private UnregisteredCoupon findUnregisteredCoupon(String couponCode) {
        return unregisteredCouponRepository.findByCouponCode(couponCode)
            .orElseThrow(UnregisteredCouponNotFoundException::new);
    }

    private void saveCouponHistory(CouponHistory couponHistory) {
        couponHistory = couponHistoryRepository.save(couponHistory);
        pushAlarmPublisher.publishEvent(couponHistory);
    }
}
