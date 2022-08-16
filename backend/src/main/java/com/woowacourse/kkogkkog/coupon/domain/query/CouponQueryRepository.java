package com.woowacourse.kkogkkog.coupon.domain.query;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponQueryRepository extends JpaRepository<Coupon, Long> {

    @Query(
        "SELECT new com.woowacourse.kkogkkog.coupon.domain.query.CouponReservationData(c.id, r.id, c.receiver.id, c.receiver.nickname.value, c.receiver.imageUrl, c.hashtag, c.description, c.couponType, c.couponStatus, r.message, r.meetingDate)"
            + " FROM Coupon c"
            + " JOIN c.sender m"
            + " LEFT JOIN Reservation r ON r.coupon = c WHERE m = :member"
            + " AND (r.coupon is NULL OR r.reservationStatus NOT IN com.woowacourse.kkogkkog.reservation.domain.ReservationStatus.CANCELED)"
            + " ORDER BY r.meetingDate DESC")
    List<CouponReservationData> findAllBySender(@Param("member") Member member);

    @Query(
        "SELECT new com.woowacourse.kkogkkog.coupon.domain.query.CouponReservationData(c.id, r.id, c.sender.id, c.sender.nickname.value, c.sender.imageUrl, c.hashtag, c.description, c.couponType, c.couponStatus, r.message, r.meetingDate)"
            + " FROM Coupon c"
            + " JOIN c.receiver m"
            + " LEFT JOIN Reservation r ON r.coupon = c WHERE m = :member"
            + " AND (r.coupon is NULL OR r.reservationStatus NOT IN com.woowacourse.kkogkkog.reservation.domain.ReservationStatus.CANCELED)"
            + " ORDER BY r.meetingDate DESC")
    List<CouponReservationData> findAllByReceiver(@Param("member") Member member);

    @Query(
        "SELECT new com.woowacourse.kkogkkog.coupon.domain.query.CouponDetailData(c.id, c.sender.id, c.sender.nickname.value, c.sender.imageUrl, c.receiver.id, c.receiver.nickname.value, c.receiver.imageUrl, c.hashtag, c.description, c.couponType, c.couponStatus, r.meetingDate, r.id)"
            + " FROM Coupon c"
            + " LEFT JOIN Reservation r ON r.coupon = c WHERE c.id = :couponId")
    CouponDetailData findCouponWithMeetingDate(@Param("couponId") Long couponId);
}
