package com.woowacourse.kkogkkog.coupon.application;

import com.woowacourse.kkogkkog.coupon.application.dto.CouponReservationResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponSaveRequest;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.query.CouponQueryRepository;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.exception.member.MemberNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CouponService {

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final CouponQueryRepository couponQueryRepository;

    public CouponService(MemberRepository memberRepository,
                         CouponRepository couponRepository,
                         CouponQueryRepository couponQueryRepository) {
        this.memberRepository = memberRepository;
        this.couponRepository = couponRepository;
        this.couponQueryRepository = couponQueryRepository;
    }

    @Transactional(readOnly = true)
    public List<CouponReservationResponse> findAllBySender(Long senderId) {
        Member findSender = findMember(senderId);
        return couponQueryRepository.findAllBySender(findSender).stream()
            .map(it -> CouponReservationResponse.of(it))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CouponReservationResponse> findAllByReceiver(Long receiverId) {
        Member findSender = findMember(receiverId);
        return couponQueryRepository.findAllByReceiver(findSender).stream()
            .map(it -> CouponReservationResponse.of(it))
            .collect(Collectors.toList());
    }

    public List<CouponResponse> save(CouponSaveRequest request) {
        Member findSender = findMember(request.getSenderId());
        List<Member> findReceivers = findReceivers(request.getReceiverIds());

        List<Coupon> coupons = request.toEntities(findSender, findReceivers);

        List<Coupon> saveCoupons = couponRepository.saveAll(coupons);
        return saveCoupons.stream()
            .map(CouponResponse::of)
            .collect(Collectors.toList());
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);
    }

    private List<Member> findReceivers(List<Long> memberIds) {
        List<Member> findMembers = memberRepository.findAllById(memberIds);
        if (memberIds.size() != findMembers.size()) {
            throw new MemberNotFoundException();
        }

        return findMembers;
    }
}
