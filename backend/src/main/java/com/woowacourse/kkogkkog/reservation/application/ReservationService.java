package com.woowacourse.kkogkkog.reservation.application;

import static com.woowacourse.kkogkkog.coupon.domain.CouponEvent.REQUEST;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.coupon.exception.CouponNotFoundException;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.MemberHistory;
import com.woowacourse.kkogkkog.domain.repository.MemberHistoryRepository;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.exception.member.MemberNotFoundException;
import com.woowacourse.kkogkkog.reservation.application.dto.ReservationSaveRequest;
import com.woowacourse.kkogkkog.reservation.application.dto.ReservationUpdateRequest;
import com.woowacourse.kkogkkog.reservation.domain.Reservation;
import com.woowacourse.kkogkkog.reservation.domain.repository.ReservationRepository;
import com.woowacourse.kkogkkog.reservation.exception.ReservationNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ReservationService {

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final ReservationRepository reservationRepository;
    private final MemberHistoryRepository memberHistoryRepository;

    public ReservationService(MemberRepository memberRepository, CouponRepository couponRepository,
                              ReservationRepository reservationRepository,
                              MemberHistoryRepository memberHistoryRepository) {
        this.memberRepository = memberRepository;
        this.couponRepository = couponRepository;
        this.reservationRepository = reservationRepository;
        this.memberHistoryRepository = memberHistoryRepository;
    }

    public Long save(ReservationSaveRequest request) {
        Coupon findCoupon = findCoupon(request.getCouponId());
        Member member = findMember(request.getMemberId());

        Reservation reservation = request.toEntity(findCoupon);
        reservation.changeCouponStatus(REQUEST, member);
        memberHistoryRepository.save(new MemberHistory(null, member, findCoupon.getSender(),
            findCoupon.getId(), findCoupon.getCouponType(), REQUEST, request.getMeetingDate(),
            request.getMessage()));

        return reservationRepository.save(reservation).getId();
    }

    private Coupon findCoupon(Long couponId) {
        return couponRepository.findById(couponId)
            .orElseThrow(CouponNotFoundException::new);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);
    }

    public void update(ReservationUpdateRequest request) {
        Reservation reservation = findReservation(request.getReservationId());
        Coupon coupon = reservation.getCoupon();
        Member requestMember = memberRepository.findById(request.getMemberId())
            .orElseThrow(MemberNotFoundException::new);

        coupon.changeStatus(CouponEvent.of(request.getEvent()), findMember(request.getMemberId()));
        memberHistoryRepository.save(
            new MemberHistory(null, coupon.getOppositeMember(requestMember), requestMember,
                coupon.getId(), coupon.getCouponType(), CouponEvent.of(request.getEvent()),
                reservation.getMeetingDate(), request.getMessage()));
    }

    private Reservation findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
            .orElseThrow(ReservationNotFoundException::new);
    }
}
