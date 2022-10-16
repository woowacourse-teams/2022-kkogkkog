package com.woowacourse.kkogkkog.unregisteredcoupon.domain.repository;

import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.unregisteredcoupon.domain.CouponUnregisteredCoupon;
import com.woowacourse.kkogkkog.unregisteredcoupon.domain.UnregisteredCoupon;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponUnregisteredCouponRepository extends JpaRepository<CouponUnregisteredCoupon, Long> {

    Optional<CouponUnregisteredCoupon> findByUnregisteredCoupon(UnregisteredCoupon unregisteredCoupon);

    @Query("SELECT cu "
        + "FROM CouponUnregisteredCoupon cu "
        + "JOIN FETCH cu.coupon c "
        + "WHERE c.sender = :member")
    List<CouponUnregisteredCoupon> findAllByCouponSender(@Param("member") Member member);
}
