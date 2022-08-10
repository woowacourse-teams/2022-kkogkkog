package com.woowacourse.kkogkkog.reservation.application;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.coupon.exception.CouponNotFoundException;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.exception.member.MemberNotFoundException;
import com.woowacourse.kkogkkog.reservation.application.dto.ReservationSaveRequest;
import com.woowacourse.kkogkkog.reservation.domain.Reservation;
import com.woowacourse.kkogkkog.reservation.domain.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ReservationService {

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(MemberRepository memberRepository,
                              CouponRepository couponRepository,
                              ReservationRepository reservationRepository) {
        this.memberRepository = memberRepository;
        this.couponRepository = couponRepository;
        this.reservationRepository = reservationRepository;
    }

    public Long save(ReservationSaveRequest request) {
        Coupon findCoupon = findCoupon(request.getCouponId());
        validateExistMember(findCoupon.getSender(), findCoupon.getReceiver());

        Reservation reservation = request.toEntity(findCoupon);

        return reservationRepository.save(reservation).getId();
    }

    private Coupon findCoupon(Long couponId) {
        return couponRepository.findById(couponId)
            .orElseThrow(CouponNotFoundException::new);
    }

    // 해당 로직은 추후 Member의 논리적 삭제에서 사용될 예정 (현재는 테스트 x)
    private void validateExistMember(Member sender, Member receiver) {
        boolean existSender = memberRepository.existsById(sender.getId());
        boolean existReceiver = memberRepository.existsById(receiver.getId());

        if (!existSender || !existReceiver) {
            throw new MemberNotFoundException();
        }
    }
}
