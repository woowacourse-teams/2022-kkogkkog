package com.woowacourse.kkogkkog.reservation.application;

import static com.woowacourse.kkogkkog.coupon.domain.CouponEventType.REQUEST;

import com.woowacourse.kkogkkog.coupon.domain.CouponEventType;
import com.woowacourse.kkogkkog.coupon.exception.CouponNotFoundException;
import com.woowacourse.kkogkkog.legacy_coupon.domain.LegacyCoupon;
import com.woowacourse.kkogkkog.legacy_coupon.domain.repository.LegacyCouponRepository;
import com.woowacourse.kkogkkog.legacy_member.domain.LegacyMemberHistory;
import com.woowacourse.kkogkkog.legacy_member.domain.repository.MemberHistoryRepository;
import com.woowacourse.kkogkkog.infrastructure.event.PushAlarmPublisher;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.member.exception.MemberNotFoundException;
import com.woowacourse.kkogkkog.reservation.application.dto.ReservationSaveRequest;
import com.woowacourse.kkogkkog.reservation.application.dto.ReservationUpdateRequest;
import com.woowacourse.kkogkkog.reservation.domain.Reservation;
import com.woowacourse.kkogkkog.reservation.domain.repository.ReservationRepository;
import com.woowacourse.kkogkkog.reservation.exception.ReservationNotFoundException;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ReservationService {

    private final MemberRepository memberRepository;
    private final LegacyCouponRepository couponRepository;
    private final ReservationRepository reservationRepository;
    private final MemberHistoryRepository memberHistoryRepository;
    private final PushAlarmPublisher pushAlarmPublisher;

    public ReservationService(MemberRepository memberRepository, LegacyCouponRepository couponRepository,
                              ReservationRepository reservationRepository,
                              MemberHistoryRepository memberHistoryRepository,
                              PushAlarmPublisher pushAlarmPublisher) {
        this.memberRepository = memberRepository;
        this.couponRepository = couponRepository;
        this.reservationRepository = reservationRepository;
        this.memberHistoryRepository = memberHistoryRepository;
        this.pushAlarmPublisher = pushAlarmPublisher;
    }

    public Long save(ReservationSaveRequest request) {
        LegacyCoupon findCoupon = findCoupon(request.getCouponId());
        Member loginMember = findMember(request.getMemberId());

        Reservation reservation = request.toEntity(findCoupon);
        reservation.changeCouponStatus(REQUEST, loginMember);
        LocalDateTime meetingDate = request.getMeetingDate();
        LegacyMemberHistory memberHistory = saveMemberHistory(
            findCoupon.getSender(), loginMember, findCoupon, REQUEST, meetingDate,
            request.getMessage());
        findCoupon.updateMeetingDate(meetingDate);

        pushAlarmPublisher.publishEvent(memberHistory);

        return reservationRepository.save(reservation).getId();
    }

    private LegacyCoupon findCoupon(Long couponId) {
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
        reservation.changeCouponStatus(CouponEventType.of(request.getEvent()), loginMember);

        LegacyCoupon coupon = reservation.getCoupon();
        LegacyMemberHistory memberHistory = saveMemberHistory(coupon.getOppositeMember(loginMember),
            loginMember, coupon, CouponEventType.of(request.getEvent()), reservation.getMeetingDate(),
            request.getMessage());

        if (validateCancelReservation(request)) {
            reservationRepository.delete(reservation);
            coupon.updateMeetingDate(null);
        }

        pushAlarmPublisher.publishEvent(memberHistory);
    }

    private boolean validateCancelReservation(ReservationUpdateRequest request) {
        return request.getEvent().equals("CANCEL") || request.getEvent().equals("DECLINE");
    }

    private LegacyMemberHistory saveMemberHistory(Member member, Member loginMember,
                                                  LegacyCoupon coupon, CouponEventType request,
                                                  LocalDateTime dateTime, String message) {
        LegacyMemberHistory memberHistory = new LegacyMemberHistory(null, member, loginMember,
            coupon.getId(), coupon.getCouponType(), request, dateTime, message);
        memberHistoryRepository.save(memberHistory);
        return memberHistory;
    }

    private Reservation findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
            .orElseThrow(ReservationNotFoundException::new);
    }
}
