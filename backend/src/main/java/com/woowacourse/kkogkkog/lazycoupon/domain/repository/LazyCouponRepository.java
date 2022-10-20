package com.woowacourse.kkogkkog.lazycoupon.domain.repository;

import com.woowacourse.kkogkkog.lazycoupon.domain.CouponLazyCoupon;
import com.woowacourse.kkogkkog.lazycoupon.domain.LazyCouponStatus;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LazyCouponRepository extends JpaRepository<CouponLazyCoupon, Long> {

    Optional<CouponLazyCoupon> findByLazyCouponId(Long id);

    Optional<CouponLazyCoupon> findByLazyCouponCouponCode(String couponCode);

    @Query("SELECT cuc "
        + "FROM CouponLazyCoupon cuc "
        + "JOIN FETCH cuc.lazyCoupon uc "
        + "JOIN FETCH uc.sender "
        + "WHERE uc.sender = :member AND uc.lazyCouponStatus = :status "
        + "ORDER BY cuc.id DESC")
    List<CouponLazyCoupon> findAllBySender(@Param("member") Member member,
                                           @Param("status") LazyCouponStatus status);
}
