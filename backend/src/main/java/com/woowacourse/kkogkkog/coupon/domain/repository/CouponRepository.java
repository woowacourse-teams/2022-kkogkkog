package com.woowacourse.kkogkkog.coupon.domain.repository;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("SELECT c "
        + "FROM Coupon c "
        + "JOIN Member m ON c.sender = m "
        + "WHERE m = :member "
        + "ORDER BY c.couponState.meetingDate DESC")
    List<Coupon> findAllBySender(@Param("member") Member member);

    @Query("SELECT c "
        + "FROM Coupon c "
        + "JOIN Member m ON c.receiver = m "
        + "WHERE m = :member "
        + "ORDER BY c.couponState.meetingDate DESC")
    List<Coupon> findAllByReceiver(@Param("member") Member member);
}
