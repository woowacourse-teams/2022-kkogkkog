package com.woowacourse.kkogkkog.coupon.application;

import com.woowacourse.kkogkkog.coupon.application.dto.*;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.coupon.domain.CouponHistory;
import com.woowacourse.kkogkkog.coupon.domain.CouponStatus;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponHistoryRepository;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.coupon.exception.CouponNotAccessibleException;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CouponService {

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
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
    public CouponsResponse findAllBySender(Long memberId, Pageable pageable) {
        Member member = memberRepository.get(memberId);
        Slice<Coupon> coupons = couponRepository.findAllBySender(member, pageable);

        return CouponsResponse.createSliceDto(coupons);
    }

    @Transactional(readOnly = true)
    public CouponsResponse findAllBySender(Long memberId, String couponStatus, Pageable pageable) {
        Member member = memberRepository.get(memberId);
        Slice<Coupon> coupons = couponRepository.findAllBySender(member, CouponStatus.valueOf(couponStatus), pageable);

        return CouponsResponse.createSliceDto(coupons);
    }

    @Transactional(readOnly = true)
    public CouponsResponse findAllByReceiver(Long memberId, Pageable pageable) {
        Member member = memberRepository.get(memberId);
        Slice<Coupon> coupons = couponRepository.findAllByReceiver(member, pageable);

        return CouponsResponse.createSliceDto(coupons);
    }

    @Transactional(readOnly = true)
    public CouponsResponse findAllByReceiver(Long memberId,
                                             String couponStatus,
                                             Pageable pageable) {
        Member member = memberRepository.get(memberId);
        Slice<Coupon> coupons = couponRepository.findAllByReceiver(member, CouponStatus.valueOf(couponStatus), pageable);

        return CouponsResponse.createSliceDto(coupons);
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
}
