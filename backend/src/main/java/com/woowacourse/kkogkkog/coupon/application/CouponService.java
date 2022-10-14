package com.woowacourse.kkogkkog.coupon.application;

import com.woowacourse.kkogkkog.coupon.application.dto.AcceptedCouponResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponDetailResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponMeetingData;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponSaveRequest;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponStatusRequest;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.coupon.domain.CouponHistory;
import com.woowacourse.kkogkkog.coupon.domain.CouponStatus;
import com.woowacourse.kkogkkog.coupon.domain.UnregisteredCoupon;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponHistoryRepository;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.coupon.domain.repository.UnregisteredCouponRepository;
import com.woowacourse.kkogkkog.coupon.exception.CouponNotAccessibleException;
import com.woowacourse.kkogkkog.coupon.exception.UnregisteredCouponNotFoundException;
import com.woowacourse.kkogkkog.coupon.presentation.dto.RegisterCouponCodeRequest;
import com.woowacourse.kkogkkog.infrastructure.event.PushAlarmPublisher;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.member.exception.MemberNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CouponService {

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final UnregisteredCouponRepository unregisteredCouponRepository;
    private final CouponHistoryRepository couponHistoryRepository;
    private final PushAlarmPublisher pushAlarmPublisher;

    @Transactional(readOnly = true)
    public CouponDetailResponse find(Long memberId, Long couponId) {
        Member member = memberRepository.get(memberId);
        Coupon coupon = couponRepository.get(couponId);
        if (!coupon.isSenderOrReceiver(member)) {
            throw new CouponNotAccessibleException();
        }
        List<CouponHistory> couponHistories = couponHistoryRepository.findAllByCouponIdOrderByCreatedTimeDesc(
            couponId);
        return CouponDetailResponse.of(coupon, couponHistories);
    }

    @Transactional(readOnly = true)
    public List<CouponResponse> findAllBySender(Long memberId) {
        Member member = memberRepository.get(memberId);
        return couponRepository.findAllBySender(member).stream()
            .map(CouponResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CouponResponse> findAllBySender(Long memberId, String couponStatus) {
        Member member = memberRepository.get(memberId);
        return couponRepository.findAllBySender(member, CouponStatus.valueOf(couponStatus)).stream()
            .map(CouponResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CouponResponse> findAllByReceiver(Long memberId) {
        Member member = memberRepository.get(memberId);
        return couponRepository.findAllByReceiver(member).stream()
            .map(CouponResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CouponResponse> findAllByReceiver(Long memberId,
                                                  String couponStatus) {
        Member member = memberRepository.get(memberId);
        return couponRepository.findAllByReceiver(member, CouponStatus.valueOf(couponStatus))
            .stream()
            .map(CouponResponse::of)
            .collect(Collectors.toList());
    }

    public List<CouponResponse> save(CouponSaveRequest request) {
        Member sender = memberRepository.get(request.getSenderId());
        List<Member> receivers = findReceivers(request.getReceiverIds());
        List<Coupon> coupons = request.toEntities(sender, receivers);
        return couponRepository.saveAll(coupons).stream()
            .peek(it -> saveCouponHistory(CouponHistory.ofNew(it)))
            .map(CouponResponse::of)
            .collect(Collectors.toList());
    }

    public CouponResponse saveByCouponCode(Long memberId, RegisterCouponCodeRequest couponCode) {
        Member receiver = memberRepository.get(memberId);
        UnregisteredCoupon unregisteredCoupon = findUnregisteredCoupon(couponCode.getCouponCode());
        Coupon coupon = couponRepository.save(unregisteredCoupon.registerCoupon(receiver));
        saveCouponHistory(CouponHistory.ofNewByCouponCode(coupon));
        return CouponResponse.of(coupon);
    }

    public void updateStatus(CouponStatusRequest request) {
        CouponEvent event = request.getEvent();
        Member loginMember = memberRepository.get(request.getMemberId());
        Coupon coupon = couponRepository.getWithLock(request.getCouponId());
        coupon.changeState(event, loginMember);
        saveCouponHistory(CouponHistory.of(loginMember, coupon, event, request.getMessage()));
    }

    @Transactional(readOnly = true)
    public List<AcceptedCouponResponse> findAcceptedCoupons(Long memberId) {
        Member member = memberRepository.get(memberId);
        Map<LocalDateTime, List<CouponMeetingData>> collect = couponRepository
            .findAllByMemberAndCouponStatusOrderByMeetingDate(member, LocalDate.now().atStartOfDay(), CouponStatus.ACCEPTED)
            .stream()
            .map(CouponMeetingData::of)
            .collect(Collectors.groupingBy(CouponMeetingData::getMeetingDate));

        return collect.entrySet().stream()
            .map(it -> AcceptedCouponResponse.of(it.getKey(), it.getValue()))
            .collect(Collectors.toList());
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
        pushAlarmPublisher.publishEvent(couponHistory);
    }

    private UnregisteredCoupon findUnregisteredCoupon(String couponCode) {
        return unregisteredCouponRepository.findByCouponCode(couponCode)
            .orElseThrow(UnregisteredCouponNotFoundException::new);
    }
}
