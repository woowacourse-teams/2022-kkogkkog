package com.woowacourse.kkogkkog.unregisteredcoupon.domain.repository;

import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.unregisteredcoupon.domain.CouponUnregisteredCoupon;
import com.woowacourse.kkogkkog.unregisteredcoupon.domain.UnregisteredCouponStatus;
import com.woowacourse.kkogkkog.unregisteredcoupon.exception.UnregisteredCouponNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UnregisteredCouponRepository extends JpaRepository<CouponUnregisteredCoupon, Long> {

    default CouponUnregisteredCoupon get(Long id) {
        return findById(id).orElseThrow(UnregisteredCouponNotFoundException::new);
    }

    Optional<CouponUnregisteredCoupon> findByUnregisteredCouponCouponCode(String couponCode);

    @Query("SELECT cuc "
        + "FROM CouponUnregisteredCoupon cuc "
        + "JOIN FETCH cuc.unregisteredCoupon uc "
        + "JOIN FETCH uc.sender "
        + "WHERE uc.sender = :member AND uc.unregisteredCouponStatus = :status "
        + "ORDER BY cuc.id DESC")
    List<CouponUnregisteredCoupon> findAllBySender(@Param("member") Member member,
                                                   @Param("status") UnregisteredCouponStatus status);
}
