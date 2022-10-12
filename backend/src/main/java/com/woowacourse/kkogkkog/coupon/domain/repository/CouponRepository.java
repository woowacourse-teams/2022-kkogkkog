package com.woowacourse.kkogkkog.coupon.domain.repository;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponStatus;
import com.woowacourse.kkogkkog.coupon.exception.CouponNotFoundException;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    default Coupon get(Long id) {
        return findById(id).orElseThrow(CouponNotFoundException::new);
    }

    default Coupon getWithLock(Long id) {
        return findByIdWithExclusiveLock(id).orElseThrow(CouponNotFoundException::new);
    }

    @Query("SELECT c "
        + "FROM Coupon c "
        + "JOIN FETCH c.sender "
        + "WHERE c.sender = :member "
        + "ORDER BY c.couponState.meetingDate DESC")
    List<Coupon> findAllBySender(@Param("member") Member member);

    @Query("SELECT c "
        + "FROM Coupon c "
        + "JOIN FETCH c.sender "
        + "WHERE c.sender = :member AND c.couponState.couponStatus = :status "
        + "ORDER BY c.couponState.meetingDate DESC")
    List<Coupon> findAllBySender(@Param("member") Member member,
                                 @Param("status") CouponStatus couponStatus);

    @Query("SELECT c "
        + "FROM Coupon c "
        + "JOIN FETCH c.receiver "
        + "WHERE c.receiver = :member "
        + "ORDER BY c.couponState.meetingDate DESC")
    List<Coupon> findAllByReceiver(@Param("member") Member member);

    @Query("SELECT c "
        + "FROM Coupon c "
        + "JOIN FETCH c.receiver "
        + "WHERE c.receiver = :member AND c.couponState.couponStatus = :status "
        + "ORDER BY c.couponState.meetingDate DESC")
    List<Coupon> findAllByReceiver(@Param("member") Member member,
                                   @Param("status") CouponStatus couponStatus);

    @Query("SELECT c "
        + "FROM Coupon c "
        + "JOIN FETCH c.sender "
        + "JOIN FETCH c.receiver  "
        + "WHERE (c.receiver = :member OR c.sender = :member) "
        + "AND c.couponState.meetingDate IS NOT NULL "
        + "AND c.couponState.meetingDate >= :nowDate "
        + "AND c.couponState.couponStatus = :couponStatus "
        + "ORDER BY c.couponState.meetingDate DESC")
    List<Coupon> findAllByMemberAndCouponStatusOrderByMeetingDate(@Param("member") Member member,
                                                                  @Param("nowDate") LocalDateTime nowDate,
                                                                  @Param("couponStatus") CouponStatus couponStatus);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Coupon c where c.id = :id")
    Optional<Coupon> findByIdWithExclusiveLock(@Param("id") Long id);
}
