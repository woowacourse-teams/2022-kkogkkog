package com.woowacourse.kkogkkog.coupon.domain.repository;

import com.woowacourse.kkogkkog.coupon.domain.UnregisteredCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnregisteredCouponRepository extends JpaRepository<UnregisteredCoupon, Long> {
}
