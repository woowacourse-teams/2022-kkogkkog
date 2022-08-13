package com.woowacourse.kkogkkog.reservation.application;

import static com.woowacourse.kkogkkog.coupon.domain.CouponEvent.REQUEST;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.coupon.exception.CouponNotFoundException;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.MemberHistory;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.exception.member.MemberNotFoundException;
import com.woowacourse.kkogkkog.reservation.application.dto.ReservationSaveRequest;
import com.woowacourse.kkogkkog.reservation.application.dto.ReservationUpdateRequest;
import com.woowacourse.kkogkkog.reservation.domain.Reservation;
import com.woowacourse.kkogkkog.reservation.domain.repository.ReservationRepository;
import com.woowacourse.kkogkkog.reservation.exception.ReservationNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ReservationService {

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final ReservationRepository reservationRepository;
    private final ApplicationEventPublisher publisher;

    public ReservationService(MemberRepository memberRepository, CouponRepository couponRepository,
                              ReservationRepository reservationRepository,
                              ApplicationEventPublisher publisher) {
        this.memberRepository = memberRepository;
        this.couponRepository = couponRepository;
        this.reservationRepository = reservationRepository;
        this.publisher = publisher;
    }

    public Long save(ReservationSaveRequest request) {
        Coupon findCoupon = findCoupon(request.getCouponId());
        Member loginMember = findMember(request.getMemberId());

        Reservation reservation = request.toEntity(findCoupon);
        reservation.changeCouponStatus(REQUEST, loginMember);
        MemberHistory memberHistory = new MemberHistory(null, findCoupon.getSender(), loginMember,
            findCoupon.getId(), findCoupon.getCouponType(), REQUEST, request.getMeetingDate(),
            request.getMessage());

        publisher.publishEvent(memberHistory);

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
        Member loginMember = findMember(request.getMemberId());

        Reservation reservation = findReservation(request.getReservationId());
        reservation.changeCouponStatus(
            CouponEvent.of(request.getEvent()), loginMember);
        reservation.changeStatus(CouponEvent.of(request.getEvent()));

        Coupon coupon = reservation.getCoupon();
        MemberHistory memberHistory = new MemberHistory(null, coupon.getOppositeMember(loginMember),
            loginMember, coupon.getId(), coupon.getCouponType(), CouponEvent.of(request.getEvent()),
            reservation.getMeetingDate(), request.getMessage());

        publisher.publishEvent(memberHistory);
    }

    private Reservation findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
            .orElseThrow(ReservationNotFoundException::new);
    }
}
