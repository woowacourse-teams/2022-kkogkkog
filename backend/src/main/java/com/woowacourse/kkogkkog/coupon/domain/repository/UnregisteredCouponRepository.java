package com.woowacourse.kkogkkog.coupon.domain.repository;

import com.woowacourse.kkogkkog.coupon.domain.UnregisteredCoupon;
import com.woowacourse.kkogkkog.coupon.domain.UnregisteredCouponStatus;
import com.woowacourse.kkogkkog.coupon.exception.UnregisteredCouponNotFoundException;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UnregisteredCouponRepository extends JpaRepository<UnregisteredCoupon, Long> {

    default UnregisteredCoupon get(Long id) {
        return findById(id).orElseThrow(UnregisteredCouponNotFoundException::new);
    }

    Optional<UnregisteredCoupon> findByCouponCode(String couponCode);

    @Query("SELECT c "
        + "FROM UnregisteredCoupon c "
        + "JOIN FETCH c.sender "
        + "WHERE c.sender = :member")
    List<UnregisteredCoupon> findAllBySender(@Param("member") Member member);

    @Query("SELECT c "
        + "FROM UnregisteredCoupon c "
        + "JOIN FETCH c.sender "
        + "WHERE c.sender = :member AND c.unregisteredCouponStatus = :status")
    List<UnregisteredCoupon> findAllBySender(@Param("member") Member member,
                                             @Param("status") UnregisteredCouponStatus status);
}
