package com.woowacourse.kkogkkog.legacy_coupon.domain.query;

import com.woowacourse.kkogkkog.legacy_coupon.domain.LegacyCoupon;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponQueryRepository extends JpaRepository<LegacyCoupon, Long> {

    @Query(
        "SELECT new com.woowacourse.kkogkkog.legacy_coupon.domain.query.CouponReservationData(c.id, r.id, c.receiver.id, c.receiver.nickname.value, c.receiver.imageUrl, c.hashtag, c.description, c.couponType, c.couponStatus, r.message, r.meetingDate)"
            + " FROM LegacyCoupon c"
            + " JOIN c.sender m"
            + " LEFT JOIN Reservation r ON r.coupon = c WHERE m = :member"
            + " ORDER BY r.meetingDate DESC")
    List<CouponReservationData> findAllBySender(@Param("member") Member member);

    @Query(
        "SELECT new com.woowacourse.kkogkkog.legacy_coupon.domain.query.CouponReservationData(c.id, r.id, c.sender.id, c.sender.nickname.value, c.sender.imageUrl, c.hashtag, c.description, c.couponType, c.couponStatus, r.message, r.meetingDate)"
            + " FROM LegacyCoupon c"
            + " JOIN c.receiver m"
            + " LEFT JOIN Reservation r ON r.coupon = c WHERE m = :member"
            + " ORDER BY r.meetingDate DESC")
    List<CouponReservationData> findAllByReceiver(@Param("member") Member member);

    @Query(
        "SELECT new com.woowacourse.kkogkkog.legacy_coupon.domain.query.CouponDetailData(c.id, c.sender.id, c.sender.nickname.value, c.sender.imageUrl, c.receiver.id, c.receiver.nickname.value, c.receiver.imageUrl, c.hashtag, c.description, c.couponType, c.couponStatus, r.meetingDate, r.id)"
            + " FROM LegacyCoupon c"
            + " LEFT JOIN Reservation r ON r.coupon = c WHERE c.id = :couponId")
    CouponDetailData findCouponWithMeetingDate(@Param("couponId") Long couponId);
}
